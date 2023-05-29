package com.ilham.ecommerce.service;

import com.ilham.ecommerce.entity.Pengguna;
import com.ilham.ecommerce.entity.Pesanan;
import com.ilham.ecommerce.entity.PesananLog;
import com.ilham.ecommerce.repository.PesananLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class PesananLogService {
    @Autowired
    private PesananLogRepository pesananLogRepository;

    public final static int DRAFT = 0;
    public final static int PEMBAYARAN = 10;
    public final static int PACKING = 20;
    public final static int PENGIRIMAN = 30;
    public final static int SELESAI = 40;
    public final static int DIBATALKAN = 90;

    public void createLog(String userName, Pesanan pesanan, int type, String message) {
        PesananLog p = new PesananLog();
        p.setId(UUID.randomUUID().toString());
        p.setLogMessage(message);
        p.setLogType(type);
        p.setPesanan(pesanan);
        p.setPengguna(new Pengguna(userName));
        p.setWaktu(new Date());
        pesananLogRepository.save(p);
    }

}
