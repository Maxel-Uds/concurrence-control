package com.project.concurrence.control.service.impl;

import com.project.concurrence.control.controller.requests.CreateTransactionRequest;
import com.project.concurrence.control.controller.responses.CreateTransactionResponse;
import com.project.concurrence.control.controller.responses.TransactionHistoricResponse;
import com.project.concurrence.control.controller.responses.TransactionResponse;
import com.project.concurrence.control.exception.InvalidTransactionException;
import com.project.concurrence.control.model.Transaction;
import com.project.concurrence.control.model.User;
import com.project.concurrence.control.model.enums.TransactionType;
import com.project.concurrence.control.repository.TransactionRepository;
import com.project.concurrence.control.service.TransactionService;
import com.project.concurrence.control.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private static final long DEBIT_OPERATION = -1L;

    private final UserService userService;
    private final TransactionRepository transactionRepository;

    public ResponseEntity<TransactionHistoricResponse> getTransactionHistoric(long userId) {
        User user = this.userService.findById(userId);

        List<TransactionResponse> transactions = this.transactionRepository.findTop10ByUserIdOrderByCriadoEmDesc(userId)
                .stream().map(TransactionResponse::of)
                .toList();

        return ResponseEntity.ok(TransactionHistoricResponse.of(user, transactions));
    }

    @Transactional
    public ResponseEntity<CreateTransactionResponse> createTransaction(long userId, CreateTransactionRequest request) {
        User user = this.userService.findUserByIdToUpdateBalance(userId);

        if(request.getTipo().equals(TransactionType.d.name())) this.validBalanceToDebitTransaction(user, request);

        long newBalance = calculateNewUserBalance(user, request);

        this.transactionRepository.save(Transaction.toEntity(request, user));

        user = this.userService.updateUser(user.copyWithNewBalance(newBalance));

        return ResponseEntity.ok(CreateTransactionResponse.of(user));
    }

    private void validBalanceToDebitTransaction(User user, CreateTransactionRequest request) {
        log.info("==== Validando se o usuário [{}] possui saldo suficiente para realizar o débito do valor [{}]", user.getId(), request.getValor());
        if(user.getSaldo() + user.getLimite() < request.getValor()) {
            throw new InvalidTransactionException(
                    "Saldo insuficiente",
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    HttpStatus.UNPROCESSABLE_ENTITY
            );
        }
    }

    private long calculateNewUserBalance(final User user, final CreateTransactionRequest request) {
        log.info("==== Calculando novo saldo do usuário [{}]", user.getId());
        return TransactionType.d.name().equals(request.getTipo()) ? user.getSaldo() + (DEBIT_OPERATION * request.getValor()) : user.getSaldo() + request.getValor();
    }
}
