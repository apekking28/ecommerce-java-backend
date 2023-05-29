package com.ilham.ecommerce.repository;

import com.ilham.ecommerce.entity.Pesanan;
import com.ilham.ecommerce.entity.PesananLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PesananLogRepository extends JpaRepository<PesananLog,String> {

}
