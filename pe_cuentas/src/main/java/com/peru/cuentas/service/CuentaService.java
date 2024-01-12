package com.peru.cuentas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peru.cuentas.entity.Cuenta;
import com.peru.cuentas.repository.CuentaRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    public Flux<Cuenta> findAll() {
        return cuentaRepository.findAll();
    }
    
    public Mono<Cuenta> findById(String id) {
        return cuentaRepository.findById(id);
    }

    public Mono<Cuenta> save(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public Mono<Void> delete(String id) {
        return cuentaRepository.deleteById(id);
    }

    public Mono<Cuenta> update(String id, Cuenta cuentaEntity) {
        return cuentaRepository.findById(id).flatMap(cuenta -> {
            cuenta.setTipo(cuentaEntity.getTipo());
            cuenta.setSaldo(cuentaEntity.getSaldo());
            cuenta.setClienteDNI(cuentaEntity.getClienteDNI());
            return cuentaRepository.save(cuenta);
        });
    }

    public Flux<Cuenta> findAllByClienteDNI(String dni) {
        return cuentaRepository.findAllByClienteDNI(dni);
    }

}
