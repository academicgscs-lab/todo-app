package com.training.todo.controller.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class TaskDto {

    private String id;
    private String title;
    private String description;

    private String status;

    private LocalDate dueDate;
    private LocalDate startDate;

}
