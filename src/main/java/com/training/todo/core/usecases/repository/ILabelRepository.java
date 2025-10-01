package com.training.todo.core.usecases.repository;

import lombok.Builder;

import java.util.List;
import java.util.Optional;

public interface ILabelRepository {
    Optional<DBLabel> search(String id) throws DBException;
    List<DBLabel> searchAll() throws DBException;

    @Builder
    record DBLabel(
            String id,
            String title,
            String description
    ){}


    class DBException extends Exception {
        public DBException(String message) {
            super(message);
        }
        public DBException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
