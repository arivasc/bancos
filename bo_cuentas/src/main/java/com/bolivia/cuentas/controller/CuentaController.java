package com.bolivia.cuentas.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bolivia.cuentas.ClienteNotFoundException;
import com.bolivia.cuentas.entity.Cuenta;
import com.bolivia.cuentas.service.CuentaService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cuentas")

public class CuentaController {

    WebClient webMsCliente = WebClient.create("http://bo-clientes-app:8080/clientes");
    WebClient webChile = WebClient.create("http://ch-cuentas-app:8080/cuentas");
    WebClient webPeru = WebClient.create("http://pe-cuentas-app:8080/cuentas");

    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<Cuenta> findAll() {
        return cuentaService.findAll();
    }

    @GetMapping("/cliente/{dni}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Cuenta> findAllByClienteDNI(@PathVariable String dni) {
        return cuentaService.findAllByClienteDNI(dni);
    }




    @GetMapping("/cliente/{dni}/all")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Cuenta> findAllChile(@PathVariable String dni) {
        Flux<Cuenta> cuentasChile = webChile.get().uri("/cliente/{dni}", dni)
            .retrieve().bodyToFlux(Cuenta.class);
        Flux<Cuenta> cuentasPeru = webPeru.get().uri("/cliente/{dni}", dni)
            .retrieve().bodyToFlux(Cuenta.class);
        Flux<Cuenta> cuentasBolivia = cuentaService.findAllByClienteDNI(dni);

        return Flux.merge(cuentasChile, cuentasPeru, cuentasBolivia);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Cuenta> findById(@PathVariable String id) {
        return cuentaService.findById(id);
    }

    @PostMapping("/{id}/update")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Cuenta> updateCuenta(@PathVariable String id, @RequestBody Cuenta cuentaEntity) {
        return cuentaService.update(id, cuentaEntity);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Cuenta> createCuenta(@RequestBody Cuenta cuentaEntity){
        String clienteId = cuentaEntity.getClienteDNI();
        cuentaEntity.setSaldo(0);
        cuentaEntity.setBanco("Banco de Bolivia");
        return webMsCliente.get().uri("/{id}", clienteId)
            .retrieve().onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), response -> 
                Mono.error(new ClienteNotFoundException("El cliente no existe"))
            )
            .bodyToMono(Void.class)
            .then(cuentaService.save(cuentaEntity));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteCliente(@PathVariable String id) {
        return cuentaService.delete(id);
    }

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleClienteNotFoundException(ClienteNotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "cliente no encontrado");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

}
