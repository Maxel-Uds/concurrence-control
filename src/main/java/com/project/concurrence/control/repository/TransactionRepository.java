package com.project.concurrence.control.repository;

import com.project.concurrence.control.model.Transaction;

import java.util.List;

public interface TransactionRepository {

    Transaction save(final Transaction transaction);
    List<Transaction> findTop10ByUserIdOrderByCriadoEmDesc(final Long userId);
}
