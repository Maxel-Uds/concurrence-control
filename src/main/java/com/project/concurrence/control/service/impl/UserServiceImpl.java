package com.project.concurrence.control.service.impl;

import com.project.concurrence.control.exception.UserNotFoundException;
import com.project.concurrence.control.model.User;
import com.project.concurrence.control.repository.UserRepository;
import com.project.concurrence.control.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findUserByIdToUpdateBalance(final long id) {
        log.info("==== Buscando usuário [{}] para atualizar saldo", id);
        return userRepository.findUserByIdToUpdateBalance(id)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("Nenhum usuário encontrado com o id [%s]", id),
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND
                ));
    }

    @Override
    public User findById(final long id) {
        log.info("==== Buscando usuário [{}]", id);
        return this.userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("Nenhum usuário encontrado com o id [%s]", id),
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND
                ));
    }

    @Override
    public User updateUser(User user) {
        log.info("==== Atualizando usuário [{}]", user.getId());
        return this.userRepository.update(user);
    }


}
