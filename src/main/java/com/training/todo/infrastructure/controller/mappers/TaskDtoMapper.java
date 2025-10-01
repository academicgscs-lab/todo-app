package com.training.todo.infrastructure.controller.mappers;

import com.training.todo.core.domain.ReadTask;
import com.training.todo.core.domain.Task;
import com.training.todo.infrastructure.controller.model.LabelDto;
import com.training.todo.infrastructure.controller.model.TaskDto;

public class TaskDtoMapper {
    public static TaskDto toDto(ReadTask task, LabelDto label) {
        return TaskDto.builder()
                .id(task.id())
                .title(task.title())
                .description(task.description())
                .labelDto(label)
                .startDate(task.startDate())
                .dueDate(task.dueDate())
                .build();
    }

    public static
}
