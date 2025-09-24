package com.training.todo.application;

import com.training.todo.application.exceptions.InvalidTaskException;
import com.training.todo.application.utils.TaskBuilder;
import com.training.todo.application.validation.ITaskValidation;
import com.training.todo.application.validation.creation.DateTaskValidation;
import com.training.todo.domain.Task;
import com.training.todo.domain.label.Label;
import com.training.todo.domain.label.LabelType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {

    private static LabelManager getLabelManager() {
        LabelManager labelManager = new LabelManager();
        labelManager.addLabel("Pending", "Hasn't been started", LabelType.PENDING);
        labelManager.addLabel("Completed", "Finished", LabelType.COMPLETED);
        return labelManager;
    }

    private static Task getTask(HashMap<LabelType, Label> labels) {
        TaskBuilder builder = new TaskBuilder(labels);
        builder.setBasicData("Todo", "Cool description");
        builder.setDates(LocalDate.now(), LocalDate.now().plusDays(5));
        return builder.build();
    }

    @Test
    void addTask_invalidDates_throwsException() {
        LabelManager labelManager = new LabelManager();
        Vector<ITaskValidation> creationalTaskValidations = new Vector<>();
        creationalTaskValidations.add(new DateTaskValidation());
        TaskManager manager = new TaskManager(labelManager.getLabelTypeMap(), creationalTaskValidations);

        TaskBuilder taskBuilder = new TaskBuilder(labelManager.getLabelTypeMap());
        taskBuilder.setBasicData("Task", "A task");

        LocalDate now = LocalDate.now();
        taskBuilder.setDates(now, now);
        try {
            manager.addTask(taskBuilder.build());
        } catch (InvalidTaskException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    @Test
    void addTask_validDates_noExceptions() {
        LabelManager labelManager = new LabelManager();
        Vector<ITaskValidation> creationalTaskValidations = new Vector<>();
        creationalTaskValidations.add(new DateTaskValidation());
        TaskManager manager = new TaskManager(labelManager.getLabelTypeMap(), creationalTaskValidations);

        TaskBuilder taskBuilder = new TaskBuilder(labelManager.getLabelTypeMap());
        taskBuilder.setBasicData("Task", "A task");
        LocalDate now = LocalDate.now();
        LocalDate inTwoWeeks = now.plusWeeks(2);
        taskBuilder.setDates(now, inTwoWeeks);
        try {
            manager.addTask(taskBuilder.build());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void addTask_nullValues_throwsException() {
        LabelManager labelManager = new LabelManager();
        Vector<ITaskValidation> creationalTaskValidations = new Vector<>();
        creationalTaskValidations.add(new DateTaskValidation());
        TaskManager manager = new TaskManager(labelManager.getLabelTypeMap(), creationalTaskValidations);

        TaskBuilder taskBuilder = new TaskBuilder(labelManager.getLabelTypeMap());
        try {
            manager.addTask(taskBuilder.build());
        } catch (InvalidTaskException e) {
            assertTrue(true, e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void setTaskStatus_changeToStatus_noExceptions() {
        LabelManager labelManager = getLabelManager();
        Task task = getTask(labelManager.getLabelTypeMap());
        TaskManager taskManager = new TaskManager(labelManager.getLabelTypeMap(), new Vector<>());
        try {
            taskManager.addTask(task);
        } catch (InvalidTaskException e) {
            fail(e.getMessage());
        }

        taskManager.setStatus(task, LabelType.COMPLETED);
    }
}