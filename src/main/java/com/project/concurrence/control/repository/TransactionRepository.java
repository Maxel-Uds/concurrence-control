package com.project.concurrence.control.repository;

import com.project.concurrence.control.model.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionRepository {
    Flux<Transaction> findAllByUserId(final Long userId);
    Mono<Transaction> save(final Transaction transaction);
}
