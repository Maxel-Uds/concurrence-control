package com.project.concurrence.control.repository.impl;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.locator.ClasspathSqlLocator;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.util.concurrent.Callable;

public abstract class BaseRepository {

    protected final Jdbi jdbi;
    protected String sqlFolder;
    private final Scheduler jdbcScheduler;
    private final static String SQL_ROOT_PATH = "db.sql";
    protected final ClasspathSqlLocator sqlLocator;

    public BaseRepository(final Jdbi jdbi, final Scheduler jdbcScheduler, String sqlFolder) {
        this.jdbi = jdbi;
        this.jdbcScheduler = jdbcScheduler;
        this.sqlFolder = sqlFolder;
        this.sqlLocator = ClasspathSqlLocator.create();
    }

    protected <T> Mono<T> async(final Callable<T> supplier) {
        return Mono.fromCallable(supplier)
                .subscribeOn(jdbcScheduler)
                .publishOn(jdbcScheduler);
    }

    protected String locateSql(final String fileName) {
        return sqlLocator.locate(String.format("%s.%s.%s",SQL_ROOT_PATH, sqlFolder, fileName));
    }
}
