package com.project.concurrence.control.service;

import com.project.concurrence.control.model.User;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> findById(Long id);
    Mono<User> updateUser(User user);
}
