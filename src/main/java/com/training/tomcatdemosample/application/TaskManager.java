package com.training.tomcatdemosample.application;

import com.training.tomcatdemosample.domain.Task;
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
