package com.training.todo.controller.service;

import com.training.todo.application.LabelManager;
import com.training.todo.application.TaskManager;
import com.training.todo.application.exceptions.InvalidTaskException;
import com.training.todo.application.utils.TaskBuilder;
import com.training.todo.controller.model.TaskDto;
import com.training.todo.domain.Task;

import java.util.List;

public class TaskService {
    private final TaskManager taskManager;
    private final TaskBuilder taskBuilder;
    private final LabelManager labelManager;

    public TaskService(TaskManager taskManager, LabelManager labelManager) {
        this.taskManager = taskManager;
        taskBuilder = new TaskBuilder(labelManager);
        this.labelManager = labelManager;
    }

    public void createTask(TaskDto taskDto) throws InvalidTaskException {
        taskBuilder.setBasicData(taskDto.getTitle(), taskDto.getDescription());
        taskBuilder.setDates(taskDto.getStartDate(), taskDto.getDueDate());
        taskManager.createTask(taskBuilder.build());
    }

    public TaskDto getTask(String id) {
        return TaskDto.mapToDto(taskManager.getTask(id));
    }

    public List<TaskDto> getTasks() {
        return taskManager.getValues().stream().map(TaskDto::mapToDto).toList();
    }

    public void updateTask(TaskDto taskDto) throws InvalidTaskException {
        String taskId = taskDto.getId();
        Task task = taskManager.getTask(taskId);
        task.setDescription(taskDto.getDescription());
        task.setTitle(taskDto.getTitle());
        task.setStatus(labelManager.getLabel(taskDto.getLabelDto().getId()));
        task.setStartDate(taskDto.getStartDate());
        task.setDueDate(taskDto.getDueDate());
        taskManager.updateTask(task);
    }

    public void deleteTask(String taskId) throws InvalidTaskException {
        taskManager.deleteTask(taskId);
    }
}
