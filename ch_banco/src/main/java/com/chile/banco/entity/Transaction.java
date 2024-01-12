package com.chile.banco.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data 
public class Transaction {

    @Id
    private String id;
    private String date;
    private double amount;
    private String cuentaDestino;
    private String cuentaOrigen;
    private String tipo;
    
}
