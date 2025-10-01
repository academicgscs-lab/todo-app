package com.training.todo.core.usecases;

import com.training.todo.core.domain.ReadLabel;

public interface ISearchLabelUseCase {

    record Request(String id) { }
    record Response(ReadLabel label){}

    class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }

        public ValidationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    Response execute(Request request) throws ValidationException;
}
