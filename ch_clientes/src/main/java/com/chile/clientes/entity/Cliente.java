package com.chile.clientes.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Cliente {
    
    @Id
    private String id;
    private String DNI;
    private String nombre;
    private String apellido;
    
}
