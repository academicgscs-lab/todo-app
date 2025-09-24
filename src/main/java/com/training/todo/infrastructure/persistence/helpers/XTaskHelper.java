package com.training.todo.infrastructure.persistence.helpers;

import com.training.todo.application.LabelManager;
import com.training.todo.domain.Task;
import com.training.todo.infrastructure.persistence.model.XTask;
import com.training.todo.infrastructure.persistence.utils.FileManager;
import com.training.todo.infrastructure.persistence.utils.XmlFacade;
import jakarta.xml.bind.JAXBException;

import java.nio.file.Path;
import java.util.Optional;
import java.util.Vector;

public class XTaskHelper implements ILoader<Task>, IMarshaller<Task> {

    private final XmlFacade<XTask> xml;
    private final String homePath;
    private final LabelManager labelManager;

    public XTaskHelper(String homePath, LabelManager labelManager) {
        this.homePath = homePath;
        this.labelManager = labelManager;
        xml = new XmlFacade<>(XTask.class);
    }

    @Override
    public Optional<Vector<Task>> load() {
        Optional<Vector<Path>> optionPaths = FileManager.listFiles(String.format("%s/%s", homePath, XTask.HOME));
        if (optionPaths.isEmpty()) {
            return Optional.empty();
        }

        Vector<Task> taskList = new Vector<>();
        for (Path path : optionPaths.get()) {
            try {
                Task task = XTask.mapFromXTask(xml.unmarshal(path), labelManager);
                taskList.add(task);
            } catch (JAXBException e) {
                System.out.println(e.getMessage());
            }
        }

        return Optional.of(taskList);
    }

    @Override
    public boolean marshall(Vector<Task> task) {
        for (Task t : task) {
            try {
                xml.marshall(XTask.mapToXTask(t), FileManager.createFile(homePath, XTask.HOME, t.getId()));
            } catch (JAXBException e) {
                System.out.println(e.getMessage());
            }
        }
        return true;
    }
}
