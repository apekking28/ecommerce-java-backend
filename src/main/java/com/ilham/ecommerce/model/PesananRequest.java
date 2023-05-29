package com.ilham.ecommerce.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class PesananRequest implements Serializable {
    private BigDecimal ongkir;
    private String alamatPengiriman;
    private List<KeranjangRequest> items;


}
