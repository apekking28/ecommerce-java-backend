package com.ilham.ecommerce.service;

import com.ilham.ecommerce.entity.Pengguna;
import com.ilham.ecommerce.entity.Pesanan;
import com.ilham.ecommerce.entity.PesananItem;
import com.ilham.ecommerce.entity.Produk;
import com.ilham.ecommerce.exception.BadRequestException;
import com.ilham.ecommerce.exception.ResourceNotFoundException;
import com.ilham.ecommerce.model.request.KeranjangRequest;
import com.ilham.ecommerce.model.request.PesananRequest;
import com.ilham.ecommerce.model.response.PesananResponse;
import com.ilham.ecommerce.model.StatusPesanan;
import com.ilham.ecommerce.repository.PesananItemRepository;
import com.ilham.ecommerce.repository.PesananRepository;
import com.ilham.ecommerce.repository.ProdukRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PesananService {

    @Autowired
    private ProdukRepository produkRepository;

    @Autowired
    private PesananRepository pesananRepository;

    @Autowired
    private PesananItemRepository pesananItemRepository;

    @Autowired
    private KeranjangService keranjangService;

    @Autowired
    private PesananLogService pesananLogService;

    @Transactional
    public PesananResponse create(String username, PesananRequest request) {
        Pesanan pesanan = new Pesanan();
        pesanan.setId(UUID.randomUUID().toString());
        pesanan.setTanggal(new Date());
        pesanan.setNomor(generateNomorPesanan());
        pesanan.setPengguna(new Pengguna(username));
        pesanan.setAlamatPengiriman(request.getAlamatPengiriman());
        pesanan.setStatusPesanan(StatusPesanan.DRAFT);
        pesanan.setWaktuPesan(new Date());

        List<PesananItem> items = new ArrayList<>();
        for (KeranjangRequest k : request.getItems()) {
            Produk produk = produkRepository.findById(k.getProdukId())
                    .orElseThrow(() -> new BadRequestException("Produk ID " + k.getProdukId() + " tidak ditemukan"));
            if (produk.getStock() < k.getKuantitas()) {
                throw new BadRequestException("Stok tidak mencukupi");
            }

            PesananItem pi = new PesananItem();
            pi.setId(UUID.randomUUID().toString());
            pi.setProduk(produk);
            pi.setDeskripsi(produk.getNama());
            pi.setKuantitas(k.getKuantitas());
            pi.setHarga(produk.getHarga());
            pi.setJumlah(new BigDecimal(pi.getHarga().doubleValue() * pi.getKuantitas()));
            pi.setPesanan(pesanan);
            items.add(pi);
        }

        BigDecimal jumlah = BigDecimal.ZERO;
        for (PesananItem pesananItem : items) {
            jumlah = jumlah.add(pesananItem.getJumlah());
        }

        pesanan.setJumlah(jumlah);
        pesanan.setOngkir(request.getOngkir());
        pesanan.setTotal(pesanan.getJumlah().add(pesanan.getOngkir()));

        Pesanan saved = pesananRepository.save(pesanan);
        for (PesananItem pesananItem : items) {
            pesananItemRepository.save(pesananItem);
            Produk produk = pesananItem.getProduk();
            produk.setStock(produk.getStock() - pesananItem.getKuantitas());
            produkRepository.save(produk);
            keranjangService.delete(username, produk.getId());
        }

        // catat log
        pesananLogService.createLog(username, pesanan, PesananLogService.DRAFT, "Pesanan sukses dibuat");
        PesananResponse pesananResponse = new PesananResponse(saved, items);
        return pesananResponse;

    }
    @Transactional
    public Pesanan cancelPesanan(String pesananId, String userId) {
        Pesanan pesanan = pesananRepository.findById(pesananId)
                .orElseThrow(() -> new ResourceNotFoundException("Pesanan ID " + pesananId + " tidak ditemukan"));
        if (!userId.equals(pesanan.getPengguna().getId())) {
            throw new BadRequestException("Pesanan ini hanya bisa di batalkan oleh yang bersangkutan");
        }

        if (!StatusPesanan.DRAFT.equals(pesanan.getStatusPesanan())) {
            throw new BadRequestException("Pesanan ini tidak dapat di batalkan karena sedang di proses");
        }

        pesanan.setStatusPesanan(StatusPesanan.DIBATALKAN);
        Pesanan saved = pesananRepository.save(pesanan);

        pesananLogService.createLog(userId, pesanan, PesananLogService.DIBATALKAN, "Pesanan sukses di batalkan");
        return saved;
    }

    @Transactional
    public Pesanan terimaPesanan(String pesananId, String userId) {
        Pesanan pesanan = pesananRepository.findById(pesananId)
                .orElseThrow(() -> new ResourceNotFoundException("Pesanan ID " + pesananId + " tidak ditemukan"));
        if (!userId.equals(pesanan.getPengguna().getId())) {
            throw new BadRequestException("Pesanan ini hanya bisa di batalkan oleh yang bersangkutan");
        }

        if (!StatusPesanan.PENGIRIMAN.equals(pesanan.getStatusPesanan())) {
            throw new BadRequestException("Penerima gagal, status pesanan saat ini " + pesanan.getStatusPesanan().name());
        }

        pesanan.setStatusPesanan(StatusPesanan.DIBATALKAN);
        Pesanan saved = pesananRepository.save(pesanan);

        pesananLogService.createLog(userId, pesanan, PesananLogService.DIBATALKAN, "Pesanan sukses di batalkan");
        return saved;
    }

    public List<Pesanan> findAllPesananUser(String userId, int page, int limit) {
        return pesananRepository.findByPenggunaId(userId,
                PageRequest.of(page, limit, Sort.by("waktuPesan").descending()));
    }

    public List<Pesanan> search(String filterText, int page, int limit) {
        return pesananRepository.search(filterText.toLowerCase(), PageRequest.of(page, limit, Sort.by("waktuPesanan").descending()));
    }

    @Transactional
    public Pesanan konfirmasiPembayaran(String pesananId, String userId) {
        Pesanan pesanan = pesananRepository.findById(pesananId)
                .orElseThrow(() -> new ResourceNotFoundException("Pesanan ID " + pesananId + " tidak ditemukan"));

        if (!StatusPesanan.DRAFT.equals(pesanan.getStatusPesanan())) {
            throw new BadRequestException("Konfirmasi pesanan gagal, status pesanan saat ini " + pesanan.getStatusPesanan().name());
        }

        pesanan.setStatusPesanan(StatusPesanan.PEMBAYARAN);
        Pesanan saved = pesananRepository.save(pesanan);

        pesananLogService.createLog(userId, pesanan, PesananLogService.PEMBAYARAN, "Pembayaran sukses di konfirmasi");
        return saved;
    }

    @Transactional
    public Pesanan packing(String pesananId, String userId) {
        Pesanan pesanan = pesananRepository.findById(pesananId)
                .orElseThrow(() -> new ResourceNotFoundException("Pesanan ID " + pesananId + " tidak ditemukan"));

        if (!StatusPesanan.PEMBAYARAN.equals(pesanan.getStatusPesanan())) {
            throw new BadRequestException("Packing pesanan gagal, status pesanan saat ini " + pesanan.getStatusPesanan().name());
        }

        pesanan.setStatusPesanan(StatusPesanan.PACKING);
        Pesanan saved = pesananRepository.save(pesanan);

        pesananLogService.createLog(userId, pesanan, PesananLogService.PACKING, "Pesanan sedang di siapkan");
        return saved;
    }

    @Transactional
    public Pesanan kirim(String pesananId, String userId) {
        Pesanan pesanan = pesananRepository.findById(pesananId)
                .orElseThrow(() -> new ResourceNotFoundException("Pesanan ID " + pesananId + " tidak ditemukan"));

        if (!StatusPesanan.PACKING.equals(pesanan.getStatusPesanan())) {
            throw new BadRequestException("Pengiriman pesanan gagal, status pesanan saat ini " + pesanan.getStatusPesanan().name());
        }

        pesanan.setStatusPesanan(StatusPesanan.PENGIRIMAN);
        Pesanan saved = pesananRepository.save(pesanan);

        pesananLogService.createLog(userId, pesanan, PesananLogService.PENGIRIMAN, "Pesanan sedang di kirim");
        return saved;
    }

    private String generateNomorPesanan() {
        return String.format("%016d", System.nanoTime());
    }

}
