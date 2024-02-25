package com.project.concurrence.control.repository;

import com.project.concurrence.control.model.User;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<User> findById(final Long userId);
    Mono<User> updateUser(final User user);
    Mono<User> findByIdForUpdate(final Long userId);
}
