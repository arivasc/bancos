package com.chile.cuentas.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.chile.cuentas.entity.Cuenta;

import reactor.core.publisher.Flux;

@Repository
public interface CuentaRepository extends ReactiveMongoRepository<Cuenta, String>{

    Flux<Cuenta> findAllByClienteDNI(String id);
    
}