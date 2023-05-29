package com.ilham.ecommerce.repository;

import com.ilham.ecommerce.entity.Produk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdukRepository extends JpaRepository<Produk,String> {
}
