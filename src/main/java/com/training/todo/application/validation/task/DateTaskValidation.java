package com.training.todo.application.validation.task;

import com.training.todo.application.validation.IValidator;
import com.training.todo.domain.Task;

import java.util.List;
import java.util.Optional;
import java.util.Vector;

public class DateTaskValidation implements IValidator {
    private final List<String> errorMessages;

    public DateTaskValidation() {
        this.errorMessages = new Vector<>();
    }

    public void checkNullFields(Task task) {
        if (task.getCreationDate() == null) {
            errorMessages.add("creationDate class field can't be null");
        }

        if (task.getDueDate() == null) {
            errorMessages.add("dueDate class field can't be null");
        }

        if (task.getStartDate() == null) {
            errorMessages.add("startDate class field can't be null");
        }
    }

    private void checkCreationDate(Task task) {
        if (task.getStartDate().isBefore(task.getCreationDate())) {
            errorMessages.add("startDate class field can't be before creation date%n");
        }
        if (task.getDueDate().isBefore(task.getStartDate())) {
            errorMessages.add("dueDate class field can't be before creation date%n");
        }
    }

    @Override
    public Optional<List<String>> isValid(Task task) {
        checkNullFields(task);
        if (!errorMessages.isEmpty()) {
            return Optional.of(errorMessages);
        }
        checkCreationDate(task);
        if (errorMessages.isEmpty()) {
            return Optional.empty();
        }else  {
            return Optional.of(errorMessages);
        }
    }
}
