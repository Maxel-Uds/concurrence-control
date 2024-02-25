package com.project.concurrence.control.service.impl;

import com.project.concurrence.control.model.User;
import com.project.concurrence.control.repository.UserRepository;
import com.project.concurrence.control.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public Mono<User> findUserByIdToUpdateBalance(final Long id) {
        return this.repository.findByIdForUpdate(id)
                .doOnError(throwable -> log.info("=== Erro ao buscar usuário: [{}]", throwable.getMessage()))
                .doFirst(() -> log.info("=== Buscando usúario [{}]", id));
    }

    @Override
    public Mono<User> findById(final Long id) {
        return this.repository.findById(id)
                .doOnError(throwable -> log.info("=== Erro ao buscar usuário: [{}]", throwable.getMessage()))
                .doFirst(() -> log.info("=== Buscando usúario [{}]", id));
    }

    @Override
    public Mono<User> updateUser(final User user, final Long amount) {
        return this.repository.updateUser(user, amount)
                .doOnSuccess(resp -> log.info("=== Usuário atualizado com sucesso [{}]", user))
                .doOnError(throwable -> log.info("=== Erro ao atualizar usuário: [{}]", throwable.getMessage()))
                .doFirst(() -> log.info("=== Atualizando usúario [{}]", user));
    }


}
