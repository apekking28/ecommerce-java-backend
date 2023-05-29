package com.ilham.ecommerce.repository;

import com.ilham.ecommerce.entity.Keranjang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KeranjangRepository extends JpaRepository<Keranjang,String> {

    Optional<Keranjang> findByPenggunaIdAndProdukId(String username, String produkId);

    List<Keranjang> findByPenggunaId(String userName);
}
