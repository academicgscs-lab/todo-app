package com.training.todo.infrastructure.persistence.helpers;

import com.training.todo.application.LabelManager;
import com.training.todo.application.utils.UUIDGenerator;
import com.training.todo.domain.Task;
import com.training.todo.domain.label.Label;
import com.training.todo.domain.label.LabelType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class XTaskHelperTest {

    private static LabelManager getLabelManager() {
        LabelManager labelManager = new LabelManager();
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
    void marshall_storesObjects_Passes() {
        String home = String.format("%s/%s", System.getProperty("user.dir"), "persistence");
        LabelManager labelManager = getLabelManager();
        XTaskHelper helper = new XTaskHelper(home, labelManager);

        Vector<Task> taskList = new Vector<>();
        for (int i = 0; i < 2; i++) {
            taskList.add(getTask(labelManager.getLabelTypeMap().get(LabelType.PENDING)));
        }

        helper.marshall(taskList);
    }

    @Test
    void unmarshall_loadsATask_Passes() {
        String home = String.format("%s/%s", System.getProperty("user.dir"), "persistence");
        LabelManager labelManager = getLabelManager();
        XTaskHelper helper = new XTaskHelper(home, labelManager);

        Task controlTask = getTask(labelManager.getLabelTypeMap().get(LabelType.PENDING));
        Vector<Task> list = new Vector<>();
        list.add(controlTask);
        helper.marshall(list);

        Optional<Vector<Task>> result = helper.load();
        assertFalse(result.isEmpty());
        Vector<Task> taskList = result.get();
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