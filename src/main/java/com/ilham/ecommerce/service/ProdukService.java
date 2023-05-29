package com.ilham.ecommerce.service;

import com.ilham.ecommerce.entity.Produk;
import com.ilham.ecommerce.exception.BadRequestException;
import com.ilham.ecommerce.exception.ResourceNotFoundException;
import com.ilham.ecommerce.repository.KategoryRepository;
import com.ilham.ecommerce.repository.ProdukRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class ProdukService {
    @Autowired
    private KategoryRepository kategoryRepository;

    @Autowired
    private ProdukRepository produkRepository;

    public List<Produk> findAll() {
        return produkRepository.findAll();
    }

    public Produk findById(String id) {
        return produkRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produk dengan id " + id + " tidak ditemukan"));

    }

    public Produk create(Produk produk) {
        if (!StringUtils.hasText(produk.getNama())) {
            throw new BadRequestException("Nama produk tidak boleh kosong");
        }

        if (produk.getKategori() == null) {
            throw new BadRequestException("Kategori tidak boleh kosong");
        }

        if (!StringUtils.hasText(produk.getKategori().getId())) {
            throw new BadRequestException("Kategori ID tidak boleh kosong");
        }

        kategoryRepository.findById(produk.getKategori().getId()).orElseThrow(() -> new BadRequestException("Kategori ID " + produk.getKategori().getId() + " tidak di temukan di database"));

        produk.setId(UUID.randomUUID().toString());
        return produkRepository.save(produk);
    }

    public Produk edit(Produk produk) {
        if (!StringUtils.hasText(produk.getId())) {
            throw new BadRequestException("Nama produk tidak boleh kosong");
        }

        if (!StringUtils.hasText(produk.getNama())) {
            throw new BadRequestException("Nama produk tidak boleh kosong");
        }

        if (produk.getKategori() == null) {
            throw new BadRequestException("Kategori tidak boleh kosong");
        }

        if (!StringUtils.hasText(produk.getKategori().getId())) {
            throw new BadRequestException("Kategori ID tidak boleh kosong");
        }

        kategoryRepository.findById(produk.getKategori().getId()).orElseThrow(() -> new BadRequestException("Kategori ID " + produk.getKategori().getId() + " tidak di temukan di database"));


        return produkRepository.save(produk);
    }

    public Produk ubahGambar(String id, String gambar) {
        Produk produk = findById(id);
        produk.setGambar(gambar);
        return produkRepository.save(produk);
    }

    public void deleteById(String id) {
        produkRepository.deleteById(id);
    }
}
