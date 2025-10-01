package com.training.todo.infrastructure.data_providers.xml_data_provider;

import com.training.todo.core.usecases.repository.ITaskRepository;
import com.training.todo.infrastructure.data_providers.xml_data_provider.model.XTask;
import com.training.todo.infrastructure.data_providers.xml_data_provider.utils.FolderManager;
import com.training.todo.infrastructure.data_providers.xml_data_provider.utils.XmlFacade;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class XmlTaskRepository implements ITaskRepository {
    private final XmlFacade<XTask> xmlHandler;
    private final HashMap<String, DbTask> data;
    private final FolderManager folderManager;

    public XmlTaskRepository(XmlFacade<XTask> xmlHandler, FolderManager folderManager) throws DBException {
        this.xmlHandler = xmlHandler;
        this.folderManager = folderManager;
        data = new HashMap<>();
        loadData();
    }

    @Override
    public Optional<DbTask> search(String id) throws DBException {
        loadData();
        DbTask dbTask = data.get(id);
        if (dbTask == null) {
            return Optional.empty();
        }
        return Optional.of(dbTask);
    }

    @Override
    public List<DbTask> search() throws DBException {
        loadData();
        return new ArrayList<>(data.values());
    }

    @Override
    public void add(DbTask task) throws DBException{
        putTask(task);
    }

    @Override
    public void update(DbTask task) throws DBException{
        if (!data.containsKey(task.id())) {
            throw new DBException(String.format("Task with id %s not found", task.id()));
        }
        putTask(task);
    }

    @Override
    public void delete(String id) throws DBException{
        if (!data.containsKey(id)) {
            throw new DBException(String.format("Task with id %s not found", id));
        }
        DbTask task = data.get(id);
        XTask xTask = new XTask(
                task.id(),
                task.title(),
                task.description(),
                task.statusId(),
                task.creationDate().toEpochDay(),
                task.dueDate().toEpochDay(),
                task.startDate().toEpochDay()
        );
        xTask.setDeleted(true);
        try {
            xmlHandler.marshall(xTask, folderManager.createFile(task.id()));
        } catch (JAXBException e) {
            throw new DBException(e.getMessage(), e);
        }
    }

    private void putTask(DbTask task) throws DBException {
        data.put(task.id(), task);
        XTask xTask = new XTask(
                task.id(),
                task.title(),
                task.description(),
                task.statusId(),
                task.creationDate().toEpochDay(),
                task.dueDate().toEpochDay(),
                task.startDate().toEpochDay()
        );

        try {
            xmlHandler.marshall(xTask, folderManager.createFile(task.id()));
        } catch (JAXBException e) {
            throw new DBException(e.getMessage(), e);
        }
    }

    private List<Path> readPaths() throws DBException {
        List<Path> files;
        try {
            files = folderManager.listFiles()
                    .orElseThrow(() -> new DBException("Couldn't  read files in path"));
        } catch (IOException e) {
            throw new DBException(e.getMessage(), e);
        }
        Set<String> recordedValues = data.keySet();
        return files.stream().filter(file -> !recordedValues.contains(file.toString())).collect(Collectors.toList());
    }

    private void loadData() throws DBException {
        for (Path path : readPaths()) {
            XTask task;
            try {
                task = xmlHandler.unmarshal(path);
            } catch (JAXBException e) {
                throw new DBException(e.getMessage(), e);
            }
            if (!task.isDeleted()) {
                data.put(task.getId(),
                        DbTask.builder()
                                .id(task.getId())
                                .title(task.getTitle())
                                .description(task.getDescription())
                                .statusId(task.getStatusId())
                                .startDate(LocalDate.ofEpochDay(task.getStartDateEpochDay()))
                                .creationDate(LocalDate.ofEpochDay(task.getCreationDateEpochDay()))
                                .dueDate(LocalDate.ofEpochDay(task.getDueDateEpochDay()))
                                .build()
                );
            }
        }
    }

}
