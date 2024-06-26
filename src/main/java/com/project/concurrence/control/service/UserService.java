package com.project.concurrence.control.service;

import com.project.concurrence.control.model.User;

public interface UserService {
    User findById(final long id);
    User updateUser(final User user);
    User findUserByIdToUpdateBalance(final long id);
}
