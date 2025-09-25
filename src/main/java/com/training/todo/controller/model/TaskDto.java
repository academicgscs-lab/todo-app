package com.training.todo.controller.model;

import com.training.todo.domain.Task;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TaskDto {

    private String id;
    private String title;
    private String description;

    private String status;

    private LocalDate dueDate;
    private LocalDate startDate;

    public static TaskDto mapToDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.id = task.getId();
        dto.title = task.getTitle();
        dto.description = task.getDescription();
        dto.status = task.getStatus().getName();
        dto.dueDate = task.getDueDate();
        dto.startDate = task.getStartDate();
        return dto;
    }
}
