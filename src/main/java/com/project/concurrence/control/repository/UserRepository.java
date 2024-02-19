package com.project.concurrence.control.repository;

import com.project.concurrence.control.model.User;
import io.micrometer.common.lang.NonNull;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @NonNull
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<User> findById(@NonNull Long id);
}
