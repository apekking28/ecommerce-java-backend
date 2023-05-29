package com.ilham.ecommerce.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class Kategori implements Serializable {
    @Id

    private String id;
    private String nama;


}
