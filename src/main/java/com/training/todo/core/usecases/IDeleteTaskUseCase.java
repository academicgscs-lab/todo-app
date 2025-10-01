package com.training.todo.core.usecases;

public interface IDeleteTaskUseCase {

    record Request(String id) { }
    record Response(){}

    class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }

        public ValidationException(Throwable cause) {
            super(cause);
        }
    }

    Response execute(Request request) throws ValidationException;
}
