package com.training.todo.core.usecases;

import com.training.todo.core.usecases.repository.ILabelRepository;
import com.training.todo.core.usecases.repository.ITaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ICreateTaskUseCaseTest {

    private static ILabelRepository.DBLabel getLabel() {
        return ILabelRepository.DBLabel.builder()
                .id("123")
                .title("title")
                .description("description")
                .build();
    }

    private static ITaskRepository getTaskRepository() {
        return Mockito.mock();
    }

    private static ILabelRepository getLabelRepository() {
        ILabelRepository labelRepository = Mockito.mock();
        try {
            Mockito.when(labelRepository.search("123")).thenReturn(Optional.of(getLabel()));
        } catch (ILabelRepository.DBException e) {
            fail(e.getMessage());
        }
        return labelRepository;
    }

    @Test
    void execute_validInput_noExceptionThrown() {
        ICreateTaskUseCase createTaskUseCase = new DefaultCreateTaskUseCase(getTaskRepository(), getLabelRepository());
        assertDoesNotThrow(() -> {
            createTaskUseCase.execute(new ICreateTaskUseCase.Request(
                    "Buy groceries",
                    "eggs, lettuce, avocado, snails",
                    "123",
                    LocalDate.now(),
                    LocalDate.now()
            ));
        });
    }

    @Test
    void execute_invalidLabelId_throwsValidationException() {
        ICreateTaskUseCase createTaskUseCase = new DefaultCreateTaskUseCase(getTaskRepository(), getLabelRepository());
        assertThrowsExactly(ICreateTaskUseCase.ValidationException.class, () -> createTaskUseCase.execute(new ICreateTaskUseCase.Request(
                "Buy groceries",
                "eggs, lettuce, avocado, snails",
                "124",
                LocalDate.now(),
                LocalDate.now()
        )));
    }

    @Test
    void execute_invalidDate_throwsValidationException() {
        ICreateTaskUseCase createTaskUseCase = new DefaultCreateTaskUseCase(getTaskRepository(), getLabelRepository());
        assertThrowsExactly(ICreateTaskUseCase.ValidationException.class, () -> createTaskUseCase.execute(new ICreateTaskUseCase.Request(
                "Buy groceries",
                "eggs, lettuce, avocado, snails",
                "124",
                LocalDate.now(),
                LocalDate.now().minusDays(4)
        )));
    }
}