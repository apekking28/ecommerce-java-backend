package com.ilham.ecommerce.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class LoginRequest implements Serializable {
    private String username;
    private String email;
    private String password;
}

