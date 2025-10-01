package com.training.todo.infrastructure.controller.service;

import com.training.todo.core.domain.ReadLabel;
import com.training.todo.core.domain.ReadTask;
import com.training.todo.core.usecases.*;
import com.training.todo.core.usecases.exceptions.InvalidTaskException;
import com.training.todo.core.usecases.repository.ILabelRepository;
import com.training.todo.core.usecases.repository.ITaskRepository;
import com.training.todo.infrastructure.controller.mappers.LabelDtoMapper;
import com.training.todo.infrastructure.controller.mappers.TaskDtoMapper;
import com.training.todo.infrastructure.controller.model.LabelDto;
import com.training.todo.infrastructure.controller.model.TaskDto;
import com.training.todo.core.domain.Task;
import com.training.todo.infrastructure.controller.utils.ListSegmentHelper;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class TaskService {
    private ICreateTaskUseCase createTaskUseCase;
    private IUpdateTaskUseCase updateTaskUseCase;
    private ISearchTaskUseCase searchTaskUseCase;
    private ISearchAllTaskUseCase searchAllTaskUseCase;

    private ISearchLabelUseCase searchLabelUseCase;
    private ISearchAllLabelUseCase searchAllLabelUseCase;

    public void createTask(TaskDto taskDto) throws InvalidTaskException {
        try {
            createTaskUseCase.execute(new ICreateTaskUseCase.Request(
                    taskDto.getTitle(),
                    taskDto.getDescription(),
                    taskDto.getLabelDto().getId(),
                    taskDto.getStartDate(),
                    taskDto.getDueDate()
            ));
        } catch (ICreateTaskUseCase.ValidationException e) {
            throw new InvalidTaskException("Task creation failed", e);
        }
    }

    public TaskDto getTask(String id) throws InvalidTaskException {
        ReadTask task;
        ReadLabel label;
        try {
            task = searchTaskUseCase.execute(new ISearchTaskUseCase.Request(id)).task();
        } catch (ISearchTaskUseCase.ValidationException | ITaskRepository.DBException e) {
            throw new InvalidTaskException(String.format("Task with id %s not found", id), e);
        }

        try {
            label = searchLabelUseCase.execute(new ISearchLabelUseCase.Request(task.statusId())).label();
        } catch (ISearchLabelUseCase.ValidationException e) {
            throw new InvalidTaskException(String.format("Label with id %s not found", task.statusId()), e);
        }

        return TaskDtoMapper.toDto(task, LabelDtoMapper.toDto(label));
    }

    public List<TaskDto> getTasks() throws InvalidTaskException {
        List<TaskDto> taskList;

        HashMap<String, LabelDto>  labelMap = new HashMap<>();
        try {
            searchAllLabelUseCase.execute().labelList().stream()
                    .map(LabelDtoMapper::toDto)
                    .forEach(item -> {labelMap.put(item.getId(),  item);});
        } catch (ISearchAllLabelUseCase.ValidationException | ILabelRepository.DBException e) {
            throw new InvalidTaskException("Get all label failed", e);
        }

        try {
            taskList = searchAllTaskUseCase.execute().taskList()
                    .stream()
                    .map(item -> TaskDtoMapper.toDto(item, labelMap.get(item.statusId())))
                    .toList();
        } catch (ISearchAllTaskUseCase.ValidationException | ITaskRepository.DBException e) {
            throw new InvalidTaskException("Get all tasks failed", e);
        }

        return taskList;
    }

    public List<List<TaskDto>> getTaskListSegmented() {
        return new ListSegmentHelper<>(taskManager.getValues().stream().map(TaskDto::mapToDto).toList()).getPageList();
    }

    public void updateTask(TaskDto dto) throws InvalidTaskException {
        updateTaskUseCase.execute(new IUpdateTaskUseCase.Request(
                dto.getId(),
                dto.getTitle(),
                dto.getDescription(),
                dto.getLabelDto().getId(),
                dto.getStartDate(),
                dto.getDueDate()
        ));


        String taskId = dto.getId();
        Task task = taskManager.getTask(taskId);
        task.setDescription(dto.getDescription());
        task.setTitle(dto.getTitle());
        task.setStatus(labelManager.getLabel(dto.getLabelDto().getId()));
        task.setStartDate(dto.getStartDate());
        task.setDueDate(dto.getDueDate());
        taskManager.updateTask(task);
    }

    public void deleteTask(String taskId) throws InvalidTaskException {
        taskManager.deleteTask(taskId);
    }
}
