package com.chile.banco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chile.banco.Repository.TransactionRepository;
import com.chile.banco.entity.Transaction;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Flux<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public Flux<Transaction> findByCuentaOrigen(String cuentaOrigen) {
        return transactionRepository.findByCuentaOrigen(cuentaOrigen);
    }

    public Flux<Transaction> findByCuentaDestino(String cuentaDestino) {
        return transactionRepository.findByCuentaDestino(cuentaDestino);
    }

    public Mono<Transaction> createTransaction (Transaction transaction) {
        return transactionRepository.save(transaction);
    }


    
}
