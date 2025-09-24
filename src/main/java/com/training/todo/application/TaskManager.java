package com.training.todo.application;

import com.training.todo.domain.Task;
import lombok.Getter;

import java.util.HashMap;

public class TaskManager {
    @Getter
    private final HashMap<String, Task> tasks;

    public TaskManager() {
        this.tasks = new HashMap<>();
    }

    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

}
