package com.bolivia.banco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.bank.models.Cuenta;
import com.bolivia.banco.entity.Transaction;
import com.bolivia.banco.service.TransactionService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    WebClient webMsCliente = WebClient.create("http://bo-cuentas-app:8080/cuentas");

    @Autowired
    private TransactionService transactionService;
    
    @GetMapping
    public Flux<Transaction> getAllTransactions(){
        return transactionService.findAll();
    }

    @GetMapping("/cuentaOrigen/{cuentaOrigen}")
    public Flux<Transaction> getTransactionsByCuentaOrigen(String cuentaOrigen){
        return transactionService.findByCuentaOrigen(cuentaOrigen);
    }

    @GetMapping("/cuentaDestino/{cuentaDestino}")
    public Flux<Transaction> getTransactionsByCuentaDestino(String cuentaDestino){
        return transactionService.findByCuentaDestino(cuentaDestino);
    }

    @PostMapping("/deposit")
    public Mono<Transaction> createDeposit(@RequestBody Transaction transaction){
        String cuentaDestinoId = transaction.getCuentaDestino();
        double amount = transaction.getAmount();
        Mono<Cuenta> cuentaDestinoMono = webMsCliente.get().uri("/{id}", cuentaDestinoId)
            .retrieve().bodyToMono(Cuenta.class)
            .switchIfEmpty(Mono.error(new RuntimeException("Cuenta Destino does not exist")));

        return cuentaDestinoMono
            .flatMap(cuentaDestino -> {
                cuentaDestino.setSaldo(cuentaDestino.getSaldo() + amount);
                return webMsCliente.post().uri("/{id}/update", cuentaDestinoId)
                    .bodyValue(cuentaDestino)
                    .retrieve().bodyToMono(Void.class)
                    .then(transactionService.createTransaction(transaction));
            });
    }

    @PostMapping("/withdraw")
    public Mono<Transaction> createWithdraw(@RequestBody Transaction transaction){
        String cuentaOrigenId = transaction.getCuentaOrigen();
        double amount = transaction.getAmount();
        Mono<Cuenta> cuentaOrigenMono = webMsCliente.get().uri("/{id}", cuentaOrigenId)
            .retrieve().bodyToMono(Cuenta.class)
            .switchIfEmpty(Mono.error(new RuntimeException("Cuenta Origen does not exist")));

        return cuentaOrigenMono
            .flatMap(cuentaOrigen -> {
                if (cuentaOrigen.getSaldo() < amount) {
                    return Mono.error(new RuntimeException("Insufficient funds in Cuenta Origen"));
                }
                cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - amount);
                return webMsCliente.post().uri("/{id}/update", cuentaOrigenId)
                    .bodyValue(cuentaOrigen)
                    .retrieve().bodyToMono(Void.class)
                    .then(transactionService.createTransaction(transaction));
            });
    }

    @PostMapping("/transfer")
    public Mono<Transaction> createTransaction(@RequestBody Transaction transaction){
        String cuentaOrigenId = transaction.getCuentaOrigen();
        String cuentaDestinoId = transaction.getCuentaDestino();
        double amount = transaction.getAmount();
        Mono<Cuenta> cuentaOrigenMono = webMsCliente.get().uri("/{id}", cuentaOrigenId)
            .retrieve().bodyToMono(Cuenta.class)
            .switchIfEmpty(Mono.error(new RuntimeException("Cuenta Origen does not exist")));

        Mono<Cuenta> cuentaDestinoMono = webMsCliente.get().uri("/{id}", cuentaDestinoId)
            .retrieve().bodyToMono(Cuenta.class)
            .switchIfEmpty(Mono.error(new RuntimeException("Cuenta Destino does not exist")));

        return Mono.zip(cuentaOrigenMono, cuentaDestinoMono)
            .flatMap(tuple -> {
                Cuenta cuentaOrigen = tuple.getT1();
                Cuenta cuentaDestino = tuple.getT2();
                if (cuentaOrigen.getSaldo() < amount) {
                    return Mono.error(new RuntimeException("Insufficient funds in Cuenta Origen"));
                }
                cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - amount);
                cuentaDestino.setSaldo(cuentaDestino.getSaldo() + amount);
                return webMsCliente.post().uri("/{id}/update", cuentaOrigenId)
                    .bodyValue(cuentaOrigen)
                    .retrieve().bodyToMono(Void.class)
                    .then(webMsCliente.post().uri("/{id}/update", cuentaDestinoId)
                        .bodyValue(cuentaDestino)
                        .retrieve().bodyToMono(Void.class))
                    .then(transactionService.createTransaction(transaction));
        });
    }
    
}
