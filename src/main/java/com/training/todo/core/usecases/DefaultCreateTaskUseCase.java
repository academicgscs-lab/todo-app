package com.training.todo.core.usecases;

import com.training.todo.core.domain.Label;
import com.training.todo.core.domain.Task;
import com.training.todo.core.usecases.repository.ILabelRepository;
import com.training.todo.core.usecases.repository.ITaskRepository;
import com.training.todo.core.usecases.utils.UUIDGenerator;
import com.training.todo.core.domain.validation.IValidator;
import com.training.todo.core.domain.validation.task.DateTaskValidation;
import com.training.todo.core.domain.validation.task.ObligatoryFieldsTaskValidation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

public class DefaultCreateTaskUseCase implements ICreateTaskUseCase {
    private final ITaskRepository taskRepository;
    private final ILabelRepository labelRepository;
    private final List<IValidator> validators;

    public DefaultCreateTaskUseCase(ITaskRepository taskRepository, ILabelRepository labelRepository) {
        this.taskRepository = taskRepository;
        this.labelRepository = labelRepository;
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
        ILabelRepository.DBLabel dbLabel;
        try {
            dbLabel = labelRepository.search(request.statusId()).orElseThrow(() -> new ValidationException(String.format("No such Label, %s", request.statusId())));
        } catch (ILabelRepository.DBException e) {
            throw new ValidationException("No such Label", e);
        }
        Task task = Task.builder()
                .title(request.title())
                .description(request.description())
                .startDate(request.startDate())
                .dueDate(request.dueDate())
                .status(Label.builder()
                        .id(dbLabel.id())
                        .title(dbLabel.title())
                        .description(dbLabel.description())
                        .build())
                .creationDate(LocalDate.now())
                .build();

        Optional<List<String>> validationResult = validate(task);
        if (validationResult.isPresent()) {
            StringBuilder builder = new StringBuilder();
            validationResult.get().forEach(s -> builder.append(s).append("\n"));
            throw new ValidationException(builder.toString());
        }
        task.setId(UUIDGenerator.generateUUID());

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
            taskRepository.add(dbTask);
        } catch (ITaskRepository.DBException e) {
            throw new ValidationException(e);
        }

        return new Response(task.getId());
    }
}