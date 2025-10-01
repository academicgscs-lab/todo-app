package com.training.todo.application;

import com.training.todo.core.usecases.LabelManager;
import com.training.todo.core.usecases.TaskManager;
import com.training.todo.core.usecases.exceptions.InvalidTaskException;
import com.training.todo.core.usecases.utils.TaskBuilder;
import com.training.todo.core.domain.Task;
import com.training.todo.core.domain.LabelType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {

    private static LabelManager getLabelManager() {
        LabelManager labelManager = new LabelManager();
        labelManager.addLabel("Pending", "Hasn't been started", LabelType.PENDING);
        labelManager.addLabel("Completed", "Finished", LabelType.COMPLETED);
        return labelManager;
    }

    private static Task getTask(LabelManager labelManager) {
        TaskBuilder builder = new TaskBuilder(labelManager);
        builder.setBasicData("Todo", "Cool description");
        builder.setDates(LocalDate.now(), LocalDate.now().plusDays(5));
        return builder.build();
    }

    @Test
    void createTask_invalidDates_throwsException() {
        LabelManager labelManager = new LabelManager();
        TaskManager manager = new TaskManager(labelManager);

        TaskBuilder taskBuilder = new TaskBuilder(labelManager);
        taskBuilder.setBasicData("Task", "A task");

        LocalDate now = LocalDate.now();
        taskBuilder.setDates(now, now);
        try {
            manager.createTask(taskBuilder.build());
        } catch (InvalidTaskException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    @Test
    void createTask_validDates_noExceptions() {
        LabelManager labelManager = new LabelManager();
        TaskManager manager = new TaskManager(labelManager);

        TaskBuilder taskBuilder = new TaskBuilder(labelManager);
        taskBuilder.setBasicData("Task", "A task");
        LocalDate now = LocalDate.now();
        LocalDate inTwoWeeks = now.plusWeeks(2);
        taskBuilder.setDates(now, inTwoWeeks);
        try {
            manager.createTask(taskBuilder.build());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void createTask_nullValues_throwsException() {
        LabelManager labelManager = new LabelManager();
        TaskManager manager = new TaskManager(labelManager);

        TaskBuilder taskBuilder = new TaskBuilder(labelManager);
        try {
            manager.createTask(taskBuilder.build());
        } catch (InvalidTaskException e) {
            assertTrue(true, e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void setTaskStatus_changeToStatus_noExceptions() {
        LabelManager labelManager = getLabelManager();
        Task task = getTask(labelManager);
        TaskManager taskManager = new TaskManager(labelManager);
        try {
            taskManager.createTask(task);
        } catch (InvalidTaskException e) {
            fail(e.getMessage());
        }

        taskManager.setStatus(task, LabelType.COMPLETED);
    }
}