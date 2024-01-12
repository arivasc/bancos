package com.chile.clientes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chile.clientes.entity.Cliente;
import com.chile.clientes.repository.ClienteRepository;

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

    public Mono<Cliente> findByDNI(String dni) {
        return clienteRepository.findByDNI(dni);
    }
    
}
