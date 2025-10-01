package com.training.todo.infrastructure.data_providers.xml_data_provider;

import com.training.todo.core.usecases.repository.ITaskRepository;
import com.training.todo.infrastructure.data_providers.xml_data_provider.model.XTask;
import com.training.todo.infrastructure.data_providers.xml_data_provider.utils.FolderManager;
import com.training.todo.infrastructure.data_providers.xml_data_provider.utils.XmlFacade;
import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class XmlTaskRepositoryTest {

    @Mock
    private XmlFacade<XTask> xmlFacade;
    @Mock
    private FolderManager folderManager;

    private static List<Path> getPaths() {
        return List.of(new File("src/test/resources/test.xml").toPath());
    }

    private static XTask getTask() {
        long epochDay = LocalDate.now().toEpochDay();
        return new XTask(
                "123",
                "title",
                "desc",
                "123",
                epochDay,
                epochDay,
                epochDay
        );
    }

    @BeforeEach
    void setUp() {
        Path path = new File("src/test/resources/test.xml").toPath();
        try {
            Mockito.when(xmlFacade.unmarshal(path)).thenReturn(getTask());
        } catch (JAXBException e) {
            fail(e.getMessage());
        }

        try {
            Mockito.when(folderManager.listFiles()).thenReturn(Optional.of(getPaths()));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void search_validInput_noExceptionThrown() {
        assertDoesNotThrow(() -> {
            ITaskRepository xmlTaskRepository = new XmlTaskRepository(xmlFacade, folderManager);
            xmlTaskRepository.search("123").ifPresentOrElse(
                    task -> assertEquals("123", task.id()),
                    Assertions::fail
            );
        });
    }

    @Test
    void update_validInput_noExceptionThrown() {
        assertDoesNotThrow(() -> {
            LocalDate localDate = LocalDate.now();
            ITaskRepository xmlTaskRepository = new XmlTaskRepository(xmlFacade, folderManager);
            xmlTaskRepository.update(ITaskRepository.DbTask.builder()
                    .id("123")
                    .title("updatedTitle")
                    .description("updatedDescription")
                    .startDate(localDate)
                    .dueDate(localDate)
                    .creationDate(localDate)
                    .statusId("123")
                    .build());
        });
    }

    @Test
    void delete_validInput_noExceptionThrown() {
        assertDoesNotThrow(() -> {
            ITaskRepository xmlTaskRepository = new XmlTaskRepository(xmlFacade, folderManager);
            xmlTaskRepository.delete("123");
        });
    }
}