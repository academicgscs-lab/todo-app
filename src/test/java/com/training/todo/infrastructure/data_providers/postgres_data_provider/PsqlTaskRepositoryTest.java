package com.training.todo.infrastructure.data_providers.postgres_data_provider;

import com.training.todo.core.usecases.repository.ITaskRepository;
import com.training.todo.core.usecases.utils.UUIDGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PsqlTaskRepositoryTest {

    private ITaskRepository.DbTask getTask(){
        LocalDate date = LocalDate.now();
        return ITaskRepository.DbTask.builder()
                .id(UUIDGenerator.generateUUID())
                .title("title")
                .description("description")
                .startDate(date)
                .creationDate(date)
                .dueDate(date)
                .statusId("7b54c5eb-beda-4c14-9a78-b6741ed75578")
                .build();
    }

    private ITaskRepository getRepository(){
        return new PsqlTaskRepository(
                "jdbc:postgresql://localhost:5432/devtodo",
                "postgres",
                "admin123"
        );
    }

    @Test
    void search() {
        ITaskRepository.DbTask task = getTask();
        ITaskRepository taskRepository = getRepository();
        Assertions.assertDoesNotThrow(() -> {
            taskRepository.add(task);
            taskRepository.search(task.id());
        });
    }

    @Test
    void searchAll() {
        ITaskRepository taskRepository = getRepository();
        Assertions.assertDoesNotThrow(() -> {
            List<ITaskRepository.DbTask> list = taskRepository.search();
            assertFalse(list.isEmpty());
        });
    }

    @Test
    void add_validInput_noException() {
        ITaskRepository taskRepository = getRepository();
        assertDoesNotThrow(() -> taskRepository.add(getTask()));
    }

    @Test
    void update() {
        ITaskRepository.DbTask task = getTask();
        ITaskRepository taskRepository = getRepository();
        Assertions.assertDoesNotThrow(() -> {
            taskRepository.add(task);
            taskRepository.update(task);
        });
    }

    @Test
    void delete() {
        ITaskRepository.DbTask task = getTask();
        ITaskRepository taskRepository = getRepository();
        Assertions.assertDoesNotThrow(() -> {
            taskRepository.add(task);
            taskRepository.delete(task.id());
        });
    }
}