package com.training.todo.core.usecases;

import com.training.todo.core.domain.ReadLabel;
import com.training.todo.core.usecases.repository.ILabelRepository;

import java.util.List;

public interface ISearchAllLabelUseCase {
    record Response(List<ReadLabel> labelList){}

    class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }

        public ValidationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    Response execute() throws ValidationException, ILabelRepository.DBException;
}
