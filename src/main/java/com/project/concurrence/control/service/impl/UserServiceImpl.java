package com.project.concurrence.control.service.impl;

import com.project.concurrence.control.model.User;
import com.project.concurrence.control.service.UserService;
import com.project.concurrence.control.exception.UserNotFoundException;
import com.project.concurrence.control.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    /*
        O comportamento padrão do Spring quando você chama um método anotado com @Transactional de outro método anotado com @Transactional
        é que o Spring gerencia as transações adequadamente. Se o método chamador já estiver em uma transação, o Spring continuará a usar a mesma
        transação para o método chamado. Se o método chamador não estiver em uma transação, o Spring
        iniciará uma nova transação para o método chamado.
    */

    @Override
    @Transactional
    public Mono<User> findById(final Long id) {
        return Mono.just(repository.findById(id))
                .filter(Optional::isPresent)
                .switchIfEmpty(Mono.defer(() -> Mono.error(
                    new UserNotFoundException(
                            String.format("Nenhum usuário encontrado com o id [%s]", id),
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND
                ))))
                .map(Optional::get)
                .doOnError(throwable -> log.info("=== Erro ao buscar usuário: [{}]", throwable.getMessage()))
                .doFirst(() -> log.info("=== Buscando usúario [{}]", id));
    }

    @Override
    @Transactional
    public Mono<User> updateUser(User user) {
        return Mono.just(repository.save(user))
                .doOnSuccess(resp -> log.info("=== Usuário atualizado com sucesso [{}]", user))
                .doOnError(throwable -> log.info("=== Erro ao atualizar usuário: [{}]", throwable.getMessage()))
                .doFirst(() -> log.info("=== Atualizando usúario [{}]", user));
    }


}
