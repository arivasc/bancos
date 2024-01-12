package com.bolivia.clientes.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Cliente {
    
    @Id
    private String id;
    private String dni;
    private String nombre;
    private String apellido;
    
}
