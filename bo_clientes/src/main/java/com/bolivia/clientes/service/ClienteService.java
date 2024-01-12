package com.bolivia.clientes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolivia.clientes.entity.Cliente;
import com.bolivia.clientes.repository.ClienteRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Flux<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Mono<Cliente> findById(String id) {
        return clienteRepository.findById(id);
    }

    public Mono<Cliente> createCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
    
}
