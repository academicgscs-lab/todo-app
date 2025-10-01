package com.training.todo.core.usecases.repository;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ITaskRepository {
    Optional<DbTask> search(String id) throws DBException;
    List<DbTask> search() throws DBException;
    void add(DbTask task) throws DBException;
    void update(DbTask task) throws DBException;
    void delete(String id) throws DBException;

    class DBException extends Exception {
        public DBException(String message) {
            super(message);
        }
        public DBException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    @Builder
    record DbTask(
            String id,
            String title,
            String description,
            String statusId,
            LocalDate creationDate,
            LocalDate dueDate,
            LocalDate startDate
    ) {
    }
}
