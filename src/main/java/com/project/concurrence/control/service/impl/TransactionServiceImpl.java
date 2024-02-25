package com.project.concurrence.control.service.impl;

import com.project.concurrence.control.controller.requests.CreateTransactionRequest;
import com.project.concurrence.control.controller.responses.CreateTransactionResponse;
import com.project.concurrence.control.controller.responses.TransactionHistoricResponse;
import com.project.concurrence.control.exception.InvalidTransactionException;
import com.project.concurrence.control.exception.UserNotFoundException;
import com.project.concurrence.control.model.Transaction;
import com.project.concurrence.control.model.User;
import com.project.concurrence.control.model.enums.TransactionType;
import com.project.concurrence.control.repository.TransactionRepository;
import com.project.concurrence.control.service.TransactionService;
import com.project.concurrence.control.service.UserService;
import jakarta.persistence.LockModeType;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private static final Long DEBIT_OPERATION = -1L;

    private final UserService userService;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Mono<CreateTransactionResponse> createTransaction(final CreateTransactionRequest request, final Long id) {
        return this.userService.findUserByIdToUpdateBalance(id)
                .switchIfEmpty(Mono.defer(() -> Mono.error(
                        new UserNotFoundException(
                                String.format("Nenhum usuário encontrado com o id [%s]", id),
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND
                        )))
                )
                .zipWhen(user -> this.calculateTransactionAmount(user, request))
                .flatMap(userAndAmount -> this.userService.updateUser(userAndAmount.getT1(), userAndAmount.getT2()))
                .flatMap(user -> this.transactionRepository.save(Transaction.toEntity(request, user)).thenReturn(user))
                .map(CreateTransactionResponse::of);
    }

    @Override
    @Transactional
//    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Mono<TransactionHistoricResponse> getBankStatements(Long id) {
        return this.userService.findById(id)
                .switchIfEmpty(Mono.defer(() -> Mono.error(
                        new UserNotFoundException(
                                String.format("Nenhum usuário encontrado com o id [%s]", id),
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND
                        )))
                )
                .flatMap(user ->
                        this.transactionRepository.findAllByUserId(user.getId())
                            .collectList()
                            .map(transactions -> TransactionHistoricResponse.of(user, transactions)
                ));
    }

//    private Mono<Long> calculateTransactionAmount(final User user, final CreateTransactionRequest request) {
//        return Mono.defer(() -> {
//            return switch (TransactionType.valueOf(request.getTipo())) {
//                case c -> Mono.just(request.getValor());
//                case d -> calculateDebit(user, request);
//            };
//        });
//    }
//
//    private Mono<Long> calculateDebit(final User user, final CreateTransactionRequest request) {
//        if(user.getSaldo() + user.getLimite() < request.getValor()) {
//            return Mono.error(new InvalidTransactionException(
//                    "Saldo insuficiente",
//                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
//                    HttpStatus.UNPROCESSABLE_ENTITY
//            ));
//        }
//
//        return Mono.just(DEBIT_OPERATION * request.getValor());
//    }

    private Mono<Long> calculateTransactionAmount(final User user, final CreateTransactionRequest request) {
        if(TransactionType.d.name().equals(request.getTipo()) && user.getSaldo() + user.getLimite() < request.getValor()) {
            return Mono.error(new InvalidTransactionException(
                    "Saldo insuficiente",
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    HttpStatus.UNPROCESSABLE_ENTITY
            ));
        }

        return TransactionType.d.name().equals(request.getTipo()) ? Mono.just(DEBIT_OPERATION * request.getValor()) : Mono.just(request.getValor());
    }
}
