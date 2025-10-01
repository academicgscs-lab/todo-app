package com.training.todo.core.domain;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ReadTask(
        String id,
        String title,
        String description,
        String statusId,
        LocalDate dueDate,
        LocalDate startDate) {
}
