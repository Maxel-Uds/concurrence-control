package com.project.concurrence.control.service;

import com.project.concurrence.control.model.User;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> findById(final Long id);
    Mono<User> updateUser(final User user);
    Mono<User> findUserByIdToUpdateBalance(final Long id);
}
