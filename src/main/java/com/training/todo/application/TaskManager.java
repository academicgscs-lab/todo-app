package com.training.todo.application;

import com.training.todo.application.exceptions.InvalidTaskException;
import com.training.todo.application.validation.ITaskValidation;
import com.training.todo.domain.Task;
import com.training.todo.domain.label.LabelType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

// TODO: use repository pattern for domain entities
public class TaskManager {
    private final HashMap<String, Task> taskMap;
    private final Vector<ITaskValidation> creationTaskValidation;
    private final LabelManager labelManager;

    public TaskManager(LabelManager labelManager, Vector<ITaskValidation> creationTaskValidation) {
        this.creationTaskValidation = creationTaskValidation;
        this.labelManager = labelManager;
        this.taskMap = new HashMap<>();
    }

    public void createTask(Task task) throws InvalidTaskException {
        for (ITaskValidation taskValidation : creationTaskValidation) {
            if (taskValidation.check(task)) {
                throw new InvalidTaskException("Invalid Task");
            }
        }

        taskMap.put(task.getId(), task);
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

    public Collection<Task> getValues(){
        return taskMap.values();
    }
}
