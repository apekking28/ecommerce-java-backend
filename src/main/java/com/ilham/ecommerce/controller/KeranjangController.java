package com.ilham.ecommerce.controller;

import com.ilham.ecommerce.entity.Keranjang;
import com.ilham.ecommerce.model.KeranjangRequest;
import com.ilham.ecommerce.security.service.UserDetailsImpl;
import com.ilham.ecommerce.service.KeranjangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@PreAuthorize("isAuthenticated()")
public class KeranjangController {

    @Autowired
    private KeranjangService keranjangService;

    @GetMapping("/keranjangs")
    public List<Keranjang> findByPenggunaId(@AuthenticationPrincipal UserDetailsImpl user){
        return keranjangService.findByPenggunaId(user.getUsername());
    }

    @PostMapping("/keranjangs")
    public Keranjang create(@AuthenticationPrincipal UserDetailsImpl user, @RequestBody KeranjangRequest request){
        return keranjangService.addKeranjang(user.getUsername(),request.getProdukId(),request.getKuantitas());
    }

    @PatchMapping("/keranjangs/{produkId}")
    public Keranjang update(@AuthenticationPrincipal UserDetailsImpl user, @PathVariable("produkId") String produkId,@RequestParam("kuantitas")Double  kuantitas){
        return keranjangService.updateKuantitas(user.getUsername(),produkId,kuantitas);
    }

    @DeleteMapping("/keranjangs/{produkId}")
    public void delete(@AuthenticationPrincipal UserDetailsImpl user, @PathVariable("produkId") String produkId) {
        keranjangService.delete(user.getUsername(), produkId);
    }
}
