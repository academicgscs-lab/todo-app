package com.training.todo.core.domain.validation.task;

import com.training.todo.core.domain.validation.IValidator;
import com.training.todo.core.domain.Task;

import java.util.List;
import java.util.Optional;
import java.util.Vector;

public class ObligatoryFieldsTaskValidation implements IValidator {

    private void validateField(String field, String fieldName, List<String> errorList) {
        if (field == null) {
            errorList.add(String.format("%s can't be null", fieldName));
        } else if (field.isEmpty()) {
            errorList.add(String.format("%s can't be empty", fieldName));
        } else if (field.isBlank()){
            errorList.add(String.format("%s can't be blank", fieldName));
        }
    }

    private void checkStatus(Task task, List<String> errorList) {
        if (task.getStatus() == null) {
            errorList.add("Task statusId can't be null");
        }
    }

    @Override
    public Optional<List<String>> isValid(Task task) {
        Vector<String> errorList = new Vector<>();
        validateField(task.getTitle(), "title", errorList);
        validateField(task.getDescription(), "description", errorList);
        checkStatus(task, errorList);

        if (errorList.isEmpty()) {
            return Optional.empty();
        }else  {
            return Optional.of(errorList);
        }
    }
}
