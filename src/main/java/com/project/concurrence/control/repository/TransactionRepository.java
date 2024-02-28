package com.project.concurrence.control.repository;

import com.project.concurrence.control.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findTop10ByUserIdOrderByCriadoEmDesc(Long userId);
}
