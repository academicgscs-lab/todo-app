package com.training.todo.core.usecases;

import com.training.todo.core.domain.ReadTask;
import com.training.todo.core.usecases.repository.ITaskRepository;

public class DefaultSearchTaskUseCase implements ISearchTaskUseCase {
    private final ITaskRepository taskRepository;

    public DefaultSearchTaskUseCase(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Response execute(Request request) throws ValidationException, ITaskRepository.DBException {
        ITaskRepository.DbTask dbTask = taskRepository.search(request.id())
                .orElseThrow(() -> new ValidationException("No such Task"));

        return new Response(
                ReadTask.builder()
                        .id(dbTask.id())
                        .title(dbTask.title())
                        .description(dbTask.description())
                        .statusId(dbTask.statusId())
                        .startDate(dbTask.startDate())
                        .dueDate(dbTask.dueDate())
                        .build()
        );
    }
}
