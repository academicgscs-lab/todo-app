package com.training.todo.application.validation.task;

import com.training.todo.application.validation.IValidator;
import com.training.todo.domain.Task;

import java.util.List;
import java.util.Optional;
import java.util.Vector;

public class ObligatoryFieldsTaskValidation implements IValidator {

    @Override
    public Optional<List<String>> isValid(Task task) {
        Vector<String> errors = new Vector<>();

        if (task.getTitle() == null) {
            errors.add("title field can't be null");
        }
        if (task.getDescription() == null) {
            errors.add("description field can't be null");
        }
        if (!errors.isEmpty()) {
            return Optional.of(errors);
        }

        if (task.getTitle().isEmpty()) {
            errors.add("title field can't be empty");
        }
        if (task.getDescription().isEmpty()) {
            errors.add("description field can't be empty");
        }

        if (errors.isEmpty()) {
            return Optional.empty();
        }else  {
            return Optional.of(errors);
        }
    }
}
