package com.training.todo.core.usecases;

import com.training.todo.core.domain.ReadTask;
import com.training.todo.core.usecases.repository.ITaskRepository;

public interface ISearchTaskUseCase {
    record Request(String id) { }
    record Response(ReadTask task) { }

    class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }

        public ValidationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    Response execute(Request request) throws ValidationException, ITaskRepository.DBException;
}
