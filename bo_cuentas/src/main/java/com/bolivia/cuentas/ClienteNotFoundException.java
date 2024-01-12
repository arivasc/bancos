package com.bolivia.cuentas;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ClienteNotFoundException extends RuntimeException {

    private String message;

    public ClienteNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage(){
        return message;
    }
}