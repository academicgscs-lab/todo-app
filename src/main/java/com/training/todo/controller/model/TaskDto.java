package com.training.todo.controller.model;

import com.training.todo.domain.Task;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class TaskDto {

    private String id;
    private String title;
    private String description;

    private LabelDto labelDto;

    private LocalDate dueDate;
    private LocalDate startDate;

    public static TaskDto mapToDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .labelDto(LabelDto.mapToDto(task.getStatus()))
                .dueDate(task.getDueDate())
                .startDate(task.getStartDate())
                .build();
    }
}
