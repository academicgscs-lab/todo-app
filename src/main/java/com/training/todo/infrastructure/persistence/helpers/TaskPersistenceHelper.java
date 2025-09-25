package com.training.todo.infrastructure.persistence.helpers;

import com.training.todo.application.LabelManager;
import com.training.todo.domain.Task;
import com.training.todo.infrastructure.persistence.model.XTask;
import com.training.todo.infrastructure.persistence.utils.FileManager;
import com.training.todo.infrastructure.persistence.utils.XmlFacade;
import jakarta.xml.bind.JAXBException;

import java.nio.file.Path;
import java.text.CollationElementIterator;
import java.util.Collection;
import java.util.Optional;
import java.util.Vector;

public class TaskPersistenceHelper extends PersistenceHelper<Task, XTask> {

    private final LabelManager labelManager;

    public TaskPersistenceHelper(String homePath, LabelManager labelManager) {
        super(homePath, new XmlFacade<>(XTask.class));
        this.labelManager = labelManager;
    }

    @Override
    public Optional<Collection<Task>> read() {
        Optional<Vector<Path>> optionPaths = FileManager.listFiles(String.format("%s/%s", homePath, XTask.HOME));
        if (optionPaths.isEmpty()) {
            return Optional.empty();
        }

        Vector<Task> taskList = new Vector<>();
        for (Path path : optionPaths.get()) {
            try {
                Task task = XTask.mapToTask(xml.unmarshal(path), labelManager);
                taskList.add(task);
            } catch (JAXBException e) {
                System.out.println(e.getMessage());
            }
        }

        return Optional.of(taskList);
    }

    @Override
    public boolean write(Collection<Task> items) {
        for (Task item : items) {
            try {
                xml.marshall(XTask.mapToXTask(item), FileManager.createFile(homePath, XTask.HOME, item.getId()));
            } catch (JAXBException e) {
                System.out.println(e.getMessage());
            }
        }
        return true;
    }
}
