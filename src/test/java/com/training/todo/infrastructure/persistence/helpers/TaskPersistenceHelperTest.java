package com.training.todo.infrastructure.persistence.helpers;

import com.training.todo.application.LabelManager;
import com.training.todo.application.utils.UUIDGenerator;
import com.training.todo.domain.Task;
import com.training.todo.domain.label.Label;
import com.training.todo.domain.label.LabelType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TaskPersistenceHelperTest {

    private static LabelManager getLabelManager() {
        LabelManager labelManager = new LabelManager();
        labelManager.addLabel("Complete", "Hasn't been started", LabelType.COMPLETED);
        labelManager.addLabel("Pending", "Hasn't been started", LabelType.PENDING);
        return labelManager;
    }

    private static Task getTask(Label label) {
        return Task.builder()
                .id(UUIDGenerator.generateUUID())
                .title("Random")
                .description("Find me a todo task please")
                .status(label)
                .creationDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(5))
                .startDate(LocalDate.now())
                .build();
    }

    @Test
    void write_storesObjects_Passes() {
        String home = String.format("%s/%s", System.getProperty("user.dir"), "persistence");
        LabelPersistenceHelper labelPersistenceHelper = new LabelPersistenceHelper(home);
        LabelManager labelManager = new  LabelManager();
        labelPersistenceHelper.read().ifPresent(labelManager::appendLabelCollection);

        TaskPersistenceHelper helper = new TaskPersistenceHelper(home, labelManager);

        Vector<Task> taskList = new Vector<>();
        for (int i = 0; i < 5; i++) {
            taskList.add(getTask(labelManager.getLabel(LabelType.PENDING)));
        }

        helper.write(taskList);
    }

    @Test
    void unmarshall_loadsATask_Passes() {
        String home = String.format("%s/%s", System.getProperty("user.dir"), "persistence");
        LabelManager labelManager = getLabelManager();
        TaskPersistenceHelper helper = new TaskPersistenceHelper(home, labelManager);

        Task controlTask = getTask(labelManager.getLabel(LabelType.PENDING));
        Vector<Task> list = new Vector<>();
        list.add(controlTask);
        helper.write(list);

        Optional<Collection<Task>> result = helper.read();
        assertFalse(result.isEmpty());
        Collection<Task> taskList = result.get();
        assertFalse(taskList.isEmpty());

        boolean success = false;
        for (Task task : taskList) {
            if (task.getId().equals(controlTask.getId())) {
                success = true;
                break;
            }
        }
        assertTrue(success);
    }

}