package com.training.todo.core.domain.validation;

import com.training.todo.core.domain.Task;

import java.util.List;
import java.util.Optional;

public interface IValidator {
    Optional<List<String>> isValid(Task task);

}
