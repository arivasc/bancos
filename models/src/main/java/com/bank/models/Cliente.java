package com.bank.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Cliente {

    private String id;
    private String nombre;
    private String apellido;
    private String dni;
    
}
