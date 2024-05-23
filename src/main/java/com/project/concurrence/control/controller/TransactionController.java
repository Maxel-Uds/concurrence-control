package com.project.concurrence.control.controller;

import com.project.concurrence.control.controller.requests.CreateTransactionRequest;
import com.project.concurrence.control.controller.responses.CreateTransactionResponse;
import com.project.concurrence.control.controller.responses.TransactionHistoricResponse;
import com.project.concurrence.control.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RestController
@AllArgsConstructor
public class TransactionController {

    private final TransactionService service;

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary="Realiza uma transação")
    @PostMapping(value = "/clientes/{id}/transacoes")
    public ResponseEntity<CreateTransactionResponse> createTransaction(@RequestBody @Valid final CreateTransactionRequest request, @PathVariable(value = "id") final long id) {
        log.info("=== Executando transação [{}] para o cliente [{}]", request, id);
        return service.createTransaction(id, request);
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary="Busca as 10 últimas transações")
    @GetMapping(value = "/clientes/{id}/extrato")
    public ResponseEntity<TransactionHistoricResponse> getTransactionHistoric(@PathVariable(value = "id") final long id) {
        log.info("=== Buscando extrato para o cliente [{}]", id);
        return service.getTransactionHistoric(id);
    }

}
