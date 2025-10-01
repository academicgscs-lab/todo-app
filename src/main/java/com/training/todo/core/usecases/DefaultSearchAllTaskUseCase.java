package com.training.todo.core.usecases;

import com.training.todo.core.domain.ReadTask;
import com.training.todo.core.usecases.repository.ITaskRepository;

public class DefaultSearchAllTaskUseCase implements ISearchAllTaskUseCase {
    private final ITaskRepository taskRepository;

    public DefaultSearchAllTaskUseCase(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    private ReadTask mapToReadTask(ITaskRepository.DbTask dbTask) {
        return ReadTask.builder()
                .id(dbTask.id())
                .title(dbTask.title())
                .description(dbTask.description())
                .statusId(dbTask.statusId())
                .startDate(dbTask.startDate())
                .dueDate(dbTask.dueDate())
                .build();
    }

    @Override
    public Response execute() throws ValidationException, ITaskRepository.DBException {
        return new Response(taskRepository.search().stream().map(this::mapToReadTask).toList());
    }
}
