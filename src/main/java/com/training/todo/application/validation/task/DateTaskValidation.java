package com.training.todo.application.validation.task;

import com.training.todo.application.validation.IValidator;
import com.training.todo.domain.Task;

import java.util.List;
import java.util.Optional;
import java.util.Vector;

public class DateTaskValidation implements IValidator {
    private List<String> errorMessages;

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

    private void checkDueDate(Task task) {
        if (task.getDueDate().isBefore(task.getStartDate())) {
            errorMessages.add("Target date can't be before Start date");
        }
    }

    @Override
    public Optional<List<String>> isValid(Task task) {
        errorMessages = new Vector<>();
        checkNullFields(task);
        if (!errorMessages.isEmpty()) {
            return Optional.of(errorMessages);
        }
        checkDueDate(task);
        if (errorMessages.isEmpty()) {
            return Optional.empty();
        }else  {
            return Optional.of(errorMessages);
        }
    }
}
