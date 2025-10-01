package com.training.todo.core.usecases;

import com.training.todo.core.domain.ReadTask;
import com.training.todo.core.usecases.repository.ITaskRepository;

import java.util.List;

public interface ISearchAllTaskUseCase {
    record Response(List<ReadTask> taskList) { }

    class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }

        public ValidationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    Response execute() throws ValidationException, ITaskRepository.DBException;
}
