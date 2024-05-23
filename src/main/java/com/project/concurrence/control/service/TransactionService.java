package com.project.concurrence.control.service;

import com.project.concurrence.control.controller.requests.CreateTransactionRequest;
import com.project.concurrence.control.controller.responses.CreateTransactionResponse;
import com.project.concurrence.control.controller.responses.TransactionHistoricResponse;
import org.springframework.http.ResponseEntity;

public interface TransactionService {
    ResponseEntity<CreateTransactionResponse> createTransaction(final long id, final CreateTransactionRequest request);
    ResponseEntity<TransactionHistoricResponse> getTransactionHistoric(final long id);
}
