package com.training.todo.core.usecases.utils;

import com.training.todo.core.usecases.LabelManager;
import com.training.todo.core.domain.Task;
import com.training.todo.core.domain.LabelType;

import java.time.LocalDate;

public class TaskBuilder {
    private final Task task;

    public TaskBuilder(LabelManager labelManager) {
        this.task = Task.builder().build();
        // a task is created as pending by default
        task.setStatus(labelManager.getLabel(LabelType.PENDING));
    }

    // this is a method for obligatory fields, it breaks open closed
    public void setBasicData(String title, String description) {
        task.setTitle(title);
        task.setDescription(description);
    }

    // this is a method for obligatory fields, it breaks open closed
    public void setDates(LocalDate startDate, LocalDate endDate) {
        task.setStartDate(startDate);
        task.setCreationDate(LocalDate.now());
        task.setDueDate(endDate);
    }

    public Task build() {
        return task;
    }
}
