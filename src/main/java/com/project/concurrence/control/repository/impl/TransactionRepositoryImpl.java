package com.project.concurrence.control.repository.impl;

import com.project.concurrence.control.model.Transaction;
import com.project.concurrence.control.repository.TransactionRepository;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Repository
public class TransactionRepositoryImpl extends BaseRepository implements TransactionRepository {

    public TransactionRepositoryImpl(final Jdbi jdbi, final Scheduler jdbcScheduler) {
        super(jdbi, jdbcScheduler, "transaction");
    }

    @Override
    public Flux<Transaction> findAllByUserId(final Long userId) {
        return async(() ->
                jdbi.inTransaction(handle -> handle.createQuery(locateSql("find-all-by-user-id"))
                        .bind("userId", userId)
                        .registerRowMapper(BeanMapper.factory(Transaction.class))
                        .mapToBean(Transaction.class)
                        .list()
                )).flatMapMany(Flux::fromIterable);
    }

    @Override
    public Mono<Transaction> save(Transaction transaction) {
        return async(() -> jdbi.inTransaction(handle ->
                    handle.createUpdate(locateSql("insert"))
                            .bind("userId", transaction.getUserId())
                            .bind("valor", transaction.getValor())
                            .bind("descricao", transaction.getDescricao())
                            .bind("tipo", transaction.getTipo().name())
                            .executeAndReturnGeneratedKeys("id")
                            .mapTo(Long.class)
                            .one()
                    ))
            .map(transaction::copyWithId);
    }
}
