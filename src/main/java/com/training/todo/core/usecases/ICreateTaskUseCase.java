package com.training.todo.core.usecases;

import java.time.LocalDate;

public interface ICreateTaskUseCase {
    record Request(String title, String description, String statusId, LocalDate startDate, LocalDate dueDate) { }
    record Response(String id){}

    class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }
        public ValidationException(Throwable cause) {
            super(cause);
        }

        public ValidationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    Response execute(Request request) throws ValidationException;
}
