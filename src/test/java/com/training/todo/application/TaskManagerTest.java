package com.training.todo.application;

import com.training.todo.application.exceptions.InvalidTaskException;
import com.training.todo.application.utils.TaskBuilder;
import com.training.todo.application.validation.ITaskValidation;
import com.training.todo.application.validation.creation.DateTaskValidation;
import com.training.todo.domain.Task;
import com.training.todo.domain.label.LabelType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Vector;

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
        Vector<ITaskValidation> creationalTaskValidations = new Vector<>();
        creationalTaskValidations.add(new DateTaskValidation());
        TaskManager manager = new TaskManager(labelManager, creationalTaskValidations);

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
        Vector<ITaskValidation> creationalTaskValidations = new Vector<>();
        creationalTaskValidations.add(new DateTaskValidation());
        TaskManager manager = new TaskManager(labelManager, creationalTaskValidations);

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
        Vector<ITaskValidation> creationalTaskValidations = new Vector<>();
        creationalTaskValidations.add(new DateTaskValidation());
        TaskManager manager = new TaskManager(labelManager, creationalTaskValidations);

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
        TaskManager taskManager = new TaskManager(labelManager, new Vector<>());
        try {
            taskManager.createTask(task);
        } catch (InvalidTaskException e) {
            fail(e.getMessage());
        }

        taskManager.setStatus(task, LabelType.COMPLETED);
    }
}