package com.training.todo.application.validation.creation;

import com.training.todo.application.validation.ITaskValidation;
import com.training.todo.domain.Task;

public class ObligatoryFieldsTaskValidation implements ITaskValidation {

    private final DateTaskValidation dateTaskValidation = new DateTaskValidation();


    @Override
    public boolean check(Task task) {
        boolean isValid = true;
        if (task.getTitle() == null || task.getTitle().isEmpty()) {
            isValid = false;
        }else if (task.getDescription() == null || task.getDescription().isEmpty()) {
            isValid = false;
        } else if (!dateTaskValidation.handleNullValues(task)) {
            isValid = false;
        }

        return isValid;
    }
}
