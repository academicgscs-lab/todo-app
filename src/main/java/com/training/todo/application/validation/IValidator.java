package com.training.todo.application.validation;

import com.training.todo.domain.Task;

import java.util.List;
import java.util.Optional;

public interface IValidator {
    Optional<List<String>> isValid(Task task);

}
