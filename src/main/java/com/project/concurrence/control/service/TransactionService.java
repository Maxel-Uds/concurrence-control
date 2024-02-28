package com.project.concurrence.control.service;

import com.project.concurrence.control.controller.requests.CreateTransactionRequest;
import com.project.concurrence.control.controller.responses.CreateTransactionResponse;
import com.project.concurrence.control.controller.responses.TransactionHistoricResponse;
import reactor.core.publisher.Mono;

public interface TransactionService {
    Mono<CreateTransactionResponse> createTransaction(final Long id, final CreateTransactionRequest request);
    Mono<TransactionHistoricResponse> getTransactionHistoric(final Long id);
}
