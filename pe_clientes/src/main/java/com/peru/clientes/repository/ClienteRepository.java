package com.peru.clientes.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.peru.clientes.entity.Cliente;

@Repository
public interface ClienteRepository extends ReactiveMongoRepository<Cliente, String>{
    
}
