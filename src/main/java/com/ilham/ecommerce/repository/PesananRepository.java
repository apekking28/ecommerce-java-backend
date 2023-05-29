package com.ilham.ecommerce.repository;

import com.ilham.ecommerce.entity.Pesanan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PesananRepository extends JpaRepository<Pesanan,String> {
    List<Pesanan> findByPenggunaId(String userId, Pageable pageable);
    @Query("SELECT p FROM Pesanan p WHERE LOWER(p.nomor) LIKE %:filterText% OR LOWER(p.pengguna.nama) LIKE %:filterText%")
    List<Pesanan> search(@Param("filterText") String filterText, Pageable pageable);
}
