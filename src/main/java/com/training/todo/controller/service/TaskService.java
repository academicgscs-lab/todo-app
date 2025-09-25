package com.training.todo.controller.service;

import com.training.todo.application.LabelManager;
import com.training.todo.application.TaskManager;
import com.training.todo.application.exceptions.InvalidTaskException;
import com.training.todo.application.utils.TaskBuilder;
import com.training.todo.controller.model.TaskDto;
import com.training.todo.domain.Task;

public class TaskService {
    private final TaskManager taskManager;
    private final TaskBuilder taskBuilder;

    public TaskService(TaskManager taskManager, LabelManager labelManager) {
        this.taskManager = taskManager;
        taskBuilder = new TaskBuilder(labelManager);
    }

    public void createTask(TaskDto taskDto) throws InvalidTaskException {
        taskManager.createTask(mapToEntity(taskDto));
    }

    public TaskDto getTask(String id)  {
        return TaskDto.mapToDto(taskManager.getTask(id));
    }

    public void updateTask(TaskDto taskDto) throws InvalidTaskException {
        taskManager.updateTask(mapToEntity(taskDto));
    }


    public Task mapToEntity(TaskDto task) {
        taskBuilder.setBasicData(task.getTitle(), task.getDescription());
        taskBuilder.setDates(task.getStartDate(), task.getDueDate());
        return taskBuilder.build();
    }
}
