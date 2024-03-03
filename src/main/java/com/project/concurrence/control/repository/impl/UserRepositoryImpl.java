package com.project.concurrence.control.repository.impl;

import com.project.concurrence.control.model.User;
import com.project.concurrence.control.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public User update(User user) {
        this.jdbcTemplate.update(
                "UPDATE users c SET saldo = ? WHERE c.id = ?",
                user.getSaldo(), user.getId()
        );

        return user;
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return this.jdbcTemplate.query(
                    "SELECT id, limite, saldo FROM users WHERE id = ?", new Object[]{id},
                    (rs, rowNum) -> new User(rs.getLong("id"), rs.getLong("saldo"), rs.getLong("limite"))
                ).stream()
                .findFirst();
    }

    @Override
    public Optional<User> findUserByIdToUpdateBalance(Long id) {
        return this.jdbcTemplate.query(
                    "SELECT id, limite, saldo FROM users WHERE id = ? FOR UPDATE", new Object[]{id},
                    (rs, rowNum) -> new User(rs.getLong("id"), rs.getLong("saldo"), rs.getLong("limite"))
                )
                .stream()
                .findFirst();

    }
}
