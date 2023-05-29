package com.ilham.ecommerce.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
public class Produk implements Serializable {
    public double kuantitas;
    @Id
    private String id;
    private String nama;
    private String deskripsi;
    private String gambar;
    @JoinColumn
    @ManyToOne
    private Kategori kategori;
    private BigDecimal harga;
    private double stock;

}
