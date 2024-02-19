package com.project.concurrence.control.service.impl;

import com.project.concurrence.control.controller.responses.CreateTransactionResponse;
import com.project.concurrence.control.repository.TransactionRepository;
import com.project.concurrence.control.controller.requests.CreateTransactionRequest;
import com.project.concurrence.control.controller.responses.TransactionHistoricResponse;
import com.project.concurrence.control.exception.InvalidTransactionException;
import com.project.concurrence.control.model.Transaction;
import com.project.concurrence.control.model.User;
import com.project.concurrence.control.service.TransactionService;
import com.project.concurrence.control.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final UserService userService;
    private final TransactionRepository repository;

    @Override
    @Transactional
    public Mono<CreateTransactionResponse> createTransaction(final CreateTransactionRequest request, final Long id) {
        return this.userService.findById(id)
                .zipWith(this.chooseOperation(request))
                .flatMap(userAndTransactionAmount -> this.updateBalance(userAndTransactionAmount.getT1(), userAndTransactionAmount.getT2()))
                .doOnSuccess(user -> Mono.just(this.repository.save(Transaction.toEntity(request, user))).thenReturn(user))
                .map(CreateTransactionResponse::of);
    }

    @Override
    public Mono<TransactionHistoricResponse> getBankStatements(Long id) {
        return this.userService.findById(id)
                .flatMap(user ->
                    Flux.fromIterable(this.repository.findByUserId(id))
                            .sort(Comparator.comparing(Transaction::getCriadoEm).reversed())
                            .collectList()
                            .map(transactions -> TransactionHistoricResponse.of(user, transactions))
                );
    }

    private Mono<Long> chooseOperation(final CreateTransactionRequest request) {
        return switch (request.getTipo()) {
            case c -> Mono.just(request.getValor());
            case d -> Mono.just(-1 * request.getValor());
        };
    }

    private Mono<User> updateBalance(final User user, final Long amount) {
        return this.validTransaction(user.getLimite(), user.getSaldo(), amount)
                .map(user::setSaldo)
                .flatMap(userService::updateUser);
    }

    private Mono<Long> validTransaction(final Long userLimit, final Long balance, final Long transactionAmount) {
        final long newBalance = balance + transactionAmount;

        if((-1 * userLimit) > newBalance) {
            return Mono.error(new InvalidTransactionException("Saldo insuficiente", HttpStatus.UNPROCESSABLE_ENTITY.value(), HttpStatus.UNPROCESSABLE_ENTITY));
        }

        return Mono.just(newBalance);
    }
}
