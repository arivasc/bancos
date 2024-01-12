package com.bank.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ErrorResponse {
    
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}