package com.training.todo.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class Task {
    private String id;
    private String title;
    private String description;

    private Label status;

    private LocalDate creationDate;
    private LocalDate dueDate;
    private LocalDate startDate;
}
