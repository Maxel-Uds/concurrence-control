package com.project.concurrence.control.repository;

import com.project.concurrence.control.model.User;

import java.util.Optional;

public interface UserRepository {

    User update(final User user);
    Optional<User> findUserById(final long id);
    Optional<User> findUserByIdToUpdateBalance(final long id);
}
