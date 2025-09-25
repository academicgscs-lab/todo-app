package com.training.todo.application.validation.task;


import com.training.todo.application.validation.IValidator;
import com.training.todo.domain.Task;

import java.util.List;
import java.util.Optional;
import java.util.Vector;

public class TaskCreationValidator implements IValidator {
    private final List<IValidator> validators;

    public TaskCreationValidator() {
        this.validators = new Vector<>();
        validators.add(new DateTaskValidation());
        validators.add(new ObligatoryFieldsTaskValidation());
    }

    @Override
    public Optional<List<String>> isValid(Task task) {
        List<String> errorMessages = new Vector<>();
        validators.forEach(validator -> validator.isValid(task).ifPresent(errorMessages::addAll));
        if (errorMessages.isEmpty()) {
            return Optional.empty();
        }else  {
            return Optional.of(errorMessages);
        }
    }
}
