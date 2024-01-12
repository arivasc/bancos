package com.peru.cuentas.repository;

import org.springframework.stereotype.Repository;

import com.peru.cuentas.entity.Cuenta;

import reactor.core.publisher.Flux;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

@Repository
public interface CuentaRepository extends ReactiveMongoRepository<Cuenta, String>{

    Flux<Cuenta> findAllByClienteDNI(String dni);
    
}