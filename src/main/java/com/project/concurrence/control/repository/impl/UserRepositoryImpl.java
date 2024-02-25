package com.project.concurrence.control.repository.impl;

import com.project.concurrence.control.model.User;
import com.project.concurrence.control.repository.UserRepository;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Repository
public class UserRepositoryImpl extends BaseRepository implements UserRepository  {

    public UserRepositoryImpl(final Jdbi jdbi, final Scheduler jdbcScheduler) { super(jdbi, jdbcScheduler, "user"); }

    @Override
    public Mono<User> findById(final Long userId) {
        return async(() -> jdbi.inTransaction(handle ->
                handle.createQuery(locateSql("find-by-id"))
                .bind("userId", userId)
                .registerRowMapper(BeanMapper.factory(User.class))
                .mapToBean(User.class)
                .findFirst()
            )
        ).flatMap(Mono::justOrEmpty);
    }

    @Override
    public Mono<User> findByIdForUpdate(final Long userId) {
        return async(() -> jdbi.inTransaction(handle ->
                        handle.createQuery(locateSql("find-by-id-for-update"))
                                .bind("userId", userId)
                                .registerRowMapper(BeanMapper.factory(User.class))
                                .mapToBean(User.class)
                                .findFirst()
                )
        ).flatMap(Mono::justOrEmpty);
    }

    @Override
    public Mono<User> updateUser(final User user) {
        return async(() -> jdbi.inTransaction(handle -> {
            handle.createUpdate(locateSql("update-user"))
                    .bind("userId", user.getId())
                    .bind("saldo", user.getSaldo())
                    .execute();

            return user;
        }));
    }
}
