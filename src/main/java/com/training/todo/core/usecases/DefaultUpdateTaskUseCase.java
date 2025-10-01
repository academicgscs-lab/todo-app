package com.training.todo.core.usecases;

import com.training.todo.core.domain.Task;
import com.training.todo.core.usecases.repository.ITaskRepository;
import com.training.todo.core.domain.validation.IValidator;
import com.training.todo.core.domain.validation.task.DateTaskValidation;
import com.training.todo.core.domain.validation.task.ObligatoryFieldsTaskValidation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

public class DefaultUpdateTaskUseCase implements IUpdateTaskUseCase {
    private final ITaskRepository taskRepository;
    private final List<IValidator> validators;

    public DefaultUpdateTaskUseCase(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.validators = new Vector<>();
        validators.add(new DateTaskValidation());
        validators.add(new ObligatoryFieldsTaskValidation());
    }

    private Optional<List<String>> validate(Task task) {
        for (IValidator validator : validators) {
            Optional<List<String>> validation = validator.isValid(task);
            if (validation.isPresent()) {
                return validation;
            }
        }
        return Optional.empty();
    }

    @Override
    public Response execute(Request request) throws ValidationException {
        if (request.id() == null || request.id().isEmpty() || request.id().isBlank()) {
            throw new ValidationException("ID required");
        }

        Task task = Task.builder()
                .id(request.id())
                .title(request.title())
                .description(request.description())
                .startDate(request.startDate())
                .dueDate(request.dueDate())
                .status(request.status())
                .creationDate(request.creationDate())
                .build();

        Optional<List<String>> validation = validate(task);
        if (validation.isPresent()) {
            StringBuilder builder = new StringBuilder();
            validation.get().forEach(s -> builder.append(s).append("\n"));
            throw new ValidationException(builder.toString());
        }

        ITaskRepository.DbTask dbTask = ITaskRepository.DbTask.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .statusId(task.getStatus().getId())
                .creationDate(task.getCreationDate())
                .startDate(task.getStartDate())
                .dueDate(task.getDueDate())
                .build();
        try {
            taskRepository.update(dbTask);
        } catch (ITaskRepository.DBException e) {
            throw new ValidationException(e);
        }
        return new Response();
    }
}
