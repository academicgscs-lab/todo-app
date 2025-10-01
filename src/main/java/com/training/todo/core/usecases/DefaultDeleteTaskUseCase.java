package com.training.todo.core.usecases;

import com.training.todo.core.usecases.repository.ITaskRepository;

public class DefaultDeleteTaskUseCase implements IDeleteTaskUseCase {
    private final ITaskRepository taskRepository;

    public DefaultDeleteTaskUseCase(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Response execute(Request request) throws ValidationException {
        try {
            taskRepository.delete(request.id());
        } catch (ITaskRepository.DBException e) {
            throw new ValidationException(e);
        }
        return new Response();
    }
}
