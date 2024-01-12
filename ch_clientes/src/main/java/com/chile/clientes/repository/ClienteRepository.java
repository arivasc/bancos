package com.chile.clientes.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.chile.clientes.entity.Cliente;

import reactor.core.publisher.Mono;

@Repository
public interface ClienteRepository extends ReactiveMongoRepository<Cliente, String>{

    Mono<Cliente> findByDNI(String dni);
    
}
