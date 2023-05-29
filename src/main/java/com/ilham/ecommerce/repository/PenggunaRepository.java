package com.ilham.ecommerce.repository;

import com.ilham.ecommerce.entity.Pengguna;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PenggunaRepository extends JpaRepository<Pengguna,String> {
    boolean existsByEmail(String email);

}
