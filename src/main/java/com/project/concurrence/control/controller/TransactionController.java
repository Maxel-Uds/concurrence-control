package com.project.concurrence.control.controller;

import com.project.concurrence.control.controller.requests.CreateTransactionRequest;
import com.project.concurrence.control.controller.responses.CreateTransactionResponse;
import com.project.concurrence.control.service.TransactionService;
import com.project.concurrence.control.controller.responses.TransactionHistoricResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@AllArgsConstructor
public class TransactionController {

    private final TransactionService service;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/clientes/{id}/transacoes")
    public Mono<CreateTransactionResponse> createTransaction(@RequestBody @Valid final CreateTransactionRequest request, @PathVariable(value = "id") final Long id) {
        return service.createTransaction(request, id)
                .doFirst(() -> log.info("=== Executando transação [{}] para o cliente [{}]", request, id))
                .doOnError(throwable -> log.info("=== Um erro ocorreu e não foi possível efetuar a transação"))
                .doOnSuccess(resp -> log.info("=== Transação efetuada com sucesso!"))
                .doFinally(signalType -> log.info("=== Finalizado a transação [{}] para o cliente [{}] com o sinal [{}]", request, id, signalType.toString()));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/clientes/{id}/extrato")
    public Mono<TransactionHistoricResponse> getTransactionHistoric(@PathVariable(value = "id") final Long id) {
        return service.getBankStatements(id)
                .doFirst(() -> log.info("=== Buscando extrato para o cliente [{}]", id))
                .doOnError(throwable -> log.info("=== Um erro ocorreu e não foi possível buscando extrato"))
                .doOnSuccess(resp -> log.info("=== Extrato encontrado com sucesso!"))
                .doFinally(signalType -> log.info("=== Finalizado a buscando de extrato para o cliente [{}] com o sinal [{}]", id, signalType.toString()));
    }

}
