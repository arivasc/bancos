package com.bolivia.cuentas.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.bolivia.cuentas.entity.Cuenta;

import reactor.core.publisher.Flux;

@Repository
public interface CuentaRepository extends ReactiveMongoRepository<Cuenta, String>{

    //Flux<Cuenta> findAllByCliente(String id);

    Flux<Cuenta> findAllByClienteDNI(String dni);
    
}