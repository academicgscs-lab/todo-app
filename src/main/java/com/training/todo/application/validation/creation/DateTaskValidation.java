package com.training.todo.application.validation.creation;

import com.training.todo.application.validation.ITaskValidation;
import com.training.todo.domain.Task;

public class DateTaskValidation implements ITaskValidation {

    public boolean handleNullValues(Task task) {
        boolean isValid = true;
        if (task.getCreationDate() == null) {
            isValid = false;
        } else if (task.getDueDate() == null) {
            isValid = false;
        }else if (task.getStartDate() == null) {
            isValid = false;
        }

        return isValid;
    }

    private boolean handleCreationDate(Task task) {
        return task.getStartDate().isBefore(task.getCreationDate()) || task.getDueDate().isBefore(task.getStartDate());
    }

    @Override
    public boolean check(Task task) {
        boolean isValid = true;

        if (!handleNullValues(task)) {
            isValid = false;
        } else if (!handleCreationDate(task)) {
            isValid = false;
        }

        return isValid;
    }
}
