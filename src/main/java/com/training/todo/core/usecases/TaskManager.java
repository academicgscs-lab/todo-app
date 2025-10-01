package com.training.todo.core.usecases;

import com.training.todo.core.usecases.exceptions.InvalidTaskException;
import com.training.todo.core.usecases.utils.UUIDGenerator;
import com.training.todo.core.domain.validation.task.TaskCreationValidator;
import com.training.todo.core.domain.validation.task.TaskUpdateValidator;
import com.training.todo.core.domain.Task;
import com.training.todo.core.domain.LabelType;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Deprecated
public class TaskManager {
    private final HashMap<String, Task> taskMap;
    private final TaskCreationValidator taskCreationValidator;
    private final TaskUpdateValidator taskUpdateValidator;
    private final LabelManager labelManager;

    public TaskManager(LabelManager labelManager) {
        taskCreationValidator = new TaskCreationValidator();
        taskUpdateValidator = new TaskUpdateValidator();
        this.labelManager = labelManager;
        this.taskMap = new HashMap<>();
    }

    public void createTask(Task task) throws InvalidTaskException {
        Optional<List<String>> validation = taskCreationValidator.isValid(task);

        if (validation.isEmpty()) {
            String id = UUIDGenerator.generateUUID();
            task.setId(id);
            taskMap.put(task.getId(), task);
        } else {
            StringBuilder messageBuilder = new StringBuilder();
            validation.get().forEach(m -> messageBuilder.append(m).append("\n"));
            throw new InvalidTaskException(messageBuilder.toString());
        }
    }

    public void appendTasks(Collection<Task> taskList) {
        taskList.forEach(task -> taskMap.put(task.getId(), task));
    }

    public void setStatus(Task task, LabelType type) throws IllegalStateException {
        task.setStatus(labelManager.getLabel(type));
    }

    public Task getTask(String id) {
        return taskMap.get(id);
    }

    public void updateTask(Task task) throws InvalidTaskException {
        Optional<List<String>> validation = taskUpdateValidator.isValid(task);
        if (validation.isEmpty()) {
            taskMap.put(task.getId(), task);
        } else {
            StringBuilder messageBuilder = new StringBuilder();
            validation.get().forEach(m -> messageBuilder.append(m).append("\n"));
            throw new InvalidTaskException(messageBuilder.toString());
        }
    }

    public void deleteTask(String id) throws InvalidTaskException {
        if (taskMap.remove(id) == null) {
            throw new InvalidTaskException(String.format("Task with id %s not found", id));
        }
    }

    public Collection<Task> getValues() {
        return taskMap.values();
    }
}
