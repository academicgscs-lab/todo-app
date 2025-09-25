package com.training.todo.controller.service;

import com.training.todo.application.LabelManager;
import com.training.todo.application.TaskManager;
import com.training.todo.application.exceptions.InvalidTaskException;
import com.training.todo.application.utils.TaskBuilder;
import com.training.todo.controller.model.TaskDto;
import com.training.todo.domain.Task;

public class TaskService {
    private final TaskManager taskManager;
    private final LabelManager labelManager;
    private final TaskBuilder taskBuilder;

    public TaskService(TaskManager taskManager, LabelManager labelManager) {
        this.taskManager = taskManager;
        this.labelManager = labelManager;
        taskBuilder = new TaskBuilder(labelManager);
    }

    public void createTask(TaskDto taskDto) throws InvalidTaskException {
        taskManager.createTask(mapToEntity(taskDto));
    }

    public static TaskDto mapToDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus().getName())
                .dueDate(task.getDueDate())
                .startDate(task.getStartDate())
                .build();
    }

    public Task mapToEntity(TaskDto task) {
        taskBuilder.setBasicData(task.getTitle(), task.getDescription());
        taskBuilder.setDates(task.getStartDate(), task.getDueDate());
        return taskBuilder.build();
    }
}
