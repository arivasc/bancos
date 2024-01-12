package com.peru.cuentas.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Cuenta {
    
    @Id
    private String id;
    private String banco;
    private String tipo;
    private double saldo;
    private String cliente;
    private String clienteDNI;
    
}
