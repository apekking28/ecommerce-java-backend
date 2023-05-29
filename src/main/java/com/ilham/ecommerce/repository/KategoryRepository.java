package com.ilham.ecommerce.repository;

import com.ilham.ecommerce.entity.Kategori;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KategoryRepository extends JpaRepository<Kategori,String> {

}
