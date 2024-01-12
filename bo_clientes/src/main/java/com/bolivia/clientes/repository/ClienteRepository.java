package com.bolivia.clientes.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.bolivia.clientes.entity.Cliente;

@Repository
public interface ClienteRepository extends ReactiveMongoRepository<Cliente, String>{
    
}
