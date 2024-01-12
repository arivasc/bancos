package com.bank.models;

import lombok.Data;

@Data
public class Cuenta {

    private String id;
    private String tipo;
    private double saldo;
    private String cliente;
    
}
