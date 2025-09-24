import com.training.todo.application.LabelManager;
import com.training.todo.application.TaskManager;
import com.training.todo.application.utils.TaskBuilder;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.fail;

class TaskManagerTest {
    @Test
    void testAddTask() {
        LabelManager labelManager = new LabelManager();
        labelManager.addLabel("label1", "label2");
        TaskManager taskManager = new TaskManager();
        TaskBuilder taskBuilder = new TaskBuilder(labelManager.getLabelMap());

        taskBuilder.stepA("Task", "A task", new Date(900000000));
        Vector<String> vector = new Vector<>();
        labelManager.getLabelMap().values().forEach(label -> vector.add(label.getId()));
        try {
            taskBuilder.stepB(vector);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        taskManager.addTask(taskBuilder.build());
    }
}