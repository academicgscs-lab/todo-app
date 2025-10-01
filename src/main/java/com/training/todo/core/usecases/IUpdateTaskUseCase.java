package com.training.todo.core.usecases;

import com.training.todo.core.domain.Label;

import java.time.LocalDate;

public interface IUpdateTaskUseCase {
    record Request(String id, String title, String description, Label status, LocalDate creationDate, LocalDate startDate, LocalDate dueDate) { }
    record Response(){}

    class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }
        public ValidationException( Throwable cause) {
            super(cause);
        }
    }

    Response execute(Request request) throws ValidationException;
}
