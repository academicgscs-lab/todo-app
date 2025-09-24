package com.training.todo.application.validation;

import com.training.todo.domain.Task;

public interface ITaskValidation {
    boolean check(Task task);
}
