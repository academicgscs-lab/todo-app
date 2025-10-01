package com.training.todo.core.domain.validation.task;

import com.training.todo.core.domain.validation.IValidator;
import com.training.todo.core.domain.Task;

import java.util.List;
import java.util.Optional;
import java.util.Vector;

public class TaskUpdateValidator implements IValidator {
    private final List<IValidator> validators;
    private List<String> errorMessages;

    public TaskUpdateValidator() {
        validators = new Vector<>();
        validators.add(new DateTaskValidation());
        validators.add(new ObligatoryFieldsTaskValidation());
    }

    private void checkId(Task task) {
        if (task.getId() == null) {
            errorMessages.add("Task id cannot be null");
        }else if (task.getId().isEmpty()) {
            errorMessages.add("Task id cannot be empty");
        }
    }

    @Override
    public Optional<List<String>> isValid(Task task) {
        errorMessages = new Vector<>();
        checkId(task);
        if (!errorMessages.isEmpty()){
            return Optional.of(errorMessages);
        }

        validators.forEach(validator -> validator.isValid(task).ifPresent(errorMessages::addAll));
        if (errorMessages.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(errorMessages);
        }
    }
}
