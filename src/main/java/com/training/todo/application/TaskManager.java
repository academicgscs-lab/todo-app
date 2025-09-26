package com.training.todo.application;

import com.training.todo.application.exceptions.InvalidTaskException;
import com.training.todo.application.validation.ITaskValidation;
import com.training.todo.domain.Task;
import com.training.todo.domain.label.Label;
import com.training.todo.domain.label.LabelType;
import lombok.Getter;

import java.util.HashMap;
import java.util.Vector;

public class TaskManager {
    @Getter
    private final HashMap<String, Task> tasks;
    private final HashMap<LabelType, Label> labelsMap;
    private final Vector<ITaskValidation> creationTaskValidation;

    public TaskManager(HashMap<LabelType, Label> labelsMap, Vector<ITaskValidation> creationTaskValidation) {
        this.labelsMap = labelsMap;
        this.creationTaskValidation = creationTaskValidation;
        this.tasks = new HashMap<>();
    }

    public void addTask(Task task) throws InvalidTaskException {
        for (ITaskValidation taskValidation : creationTaskValidation) {
            if (taskValidation.check(task)) {
                throw new InvalidTaskException("Invalid Task");
            }
        }

        tasks.put(task.getId(), task);
    }

    public void setStatus(Task task, LabelType type) throws IllegalStateException {
        task.setStatus(labelsMap.get(type));
    }
}
