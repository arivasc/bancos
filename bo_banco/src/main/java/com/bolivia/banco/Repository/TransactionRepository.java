package com.bolivia.banco.Repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.bolivia.banco.entity.Transaction;

import reactor.core.publisher.Flux;

@Repository
public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String>{

    Flux<Transaction> findByCuentaOrigen(String cuentaOrigen);

    Flux<Transaction> findByCuentaDestino(String cuentaDestino);

    

    
}
