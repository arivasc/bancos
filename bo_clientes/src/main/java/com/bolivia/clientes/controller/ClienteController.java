package com.bolivia.clientes.controller;

import com.bolivia.clientes.entity.Cliente;
import com.bolivia.clientes.service.ClienteService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public Flux<Cliente> getAllClientes(){
        return clienteService.findAll();
    }

    @GetMapping("/{dni}")
    public Mono<ResponseEntity<Object>> getCliente(@PathVariable String dni){
        return clienteService.findByDNI(dni)
        .map(cliente -> ResponseEntity.ok((Object)cliente))
        .switchIfEmpty(Mono.just(ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(Collections.singletonMap("message", "Cliente no encontrado"))));
    }

    @PostMapping
    public Mono<Cliente> createCliente(@RequestBody Cliente cliente){
        return clienteService.createCliente(cliente);
    }
    
}
