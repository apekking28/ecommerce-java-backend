package com.ilham.ecommerce.service;

import com.ilham.ecommerce.entity.Kategori;
import com.ilham.ecommerce.exception.ResourceNotFoundException;
import com.ilham.ecommerce.repository.KategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class KategoriService {

    @Autowired
    private KategoryRepository kategoryRepository;

    public Kategori findById(String id) {
        return kategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Kategori dengan id " + id + " tidak ditemukan"));
    }

    public List<Kategori> findAll() {
        return kategoryRepository.findAll();
    }

    public Kategori create(Kategori kategori) {
        kategori.setId(UUID.randomUUID().toString());
        return kategoryRepository.save(kategori);
    }

    public Kategori edit(Kategori kategori) {
        return kategoryRepository.save(kategori);
    }

    public void deleteById(String id) {
        kategoryRepository.deleteById(id);
    }

}
