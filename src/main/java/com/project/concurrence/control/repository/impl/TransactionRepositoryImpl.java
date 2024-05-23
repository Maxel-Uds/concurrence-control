package com.project.concurrence.control.repository.impl;

import com.project.concurrence.control.model.Transaction;
import com.project.concurrence.control.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@AllArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Transaction save(Transaction transaction) {
        this.jdbcTemplate.update(
                "INSERT INTO transactions (user_id, valor, tipo, descricao) VALUES (?, ?, ?, ?)",
                transaction.getUserId(), transaction.getValor(), transaction.getTipo(), transaction.getDescricao()
        );

        return transaction;
    }

    @Override
    public List<Transaction> findTop10ByUserIdOrderByCriadoEmDesc(long userId) {
        log.info("==== Buscando extrato do user [{}]", userId);
        return this.jdbcTemplate.query(
                "SELECT t.id, t.user_id, t.valor, t.tipo, t.descricao, t.criado_em FROM transactions as t WHERE t.user_id = ? ORDER BY t.criado_em DESC LIMIT 10",
                new Object[]{userId},
                (rs, rowNum) ->
                        new Transaction(
                                rs.getLong("id"),
                                rs.getLong("user_id"),
                                rs.getLong("valor"),
                                rs.getString("descricao"),
                                rs.getString("tipo"),
                                rs.getString("criado_em")
                        ));
    }
}
