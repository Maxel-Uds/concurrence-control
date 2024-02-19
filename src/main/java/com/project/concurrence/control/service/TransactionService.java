package com.project.concurrence.control.service;

import com.project.concurrence.control.controller.requests.CreateTransactionRequest;
import com.project.concurrence.control.controller.responses.CreateTransactionResponse;
import com.project.concurrence.control.controller.responses.TransactionHistoricResponse;
import reactor.core.publisher.Mono;

public interface TransactionService {
    Mono<CreateTransactionResponse> createTransaction(final CreateTransactionRequest request, final Long id);
    Mono<TransactionHistoricResponse> getBankStatements(final Long id);
}
