package com.training.todo.infrastructure.data_providers.postgres_data_provider;

import com.training.todo.core.usecases.repository.ILabelRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PsqlLabelRepositoryTest {

    private ILabelRepository getRepository(){
        return new PsqlLabelRepository(
                "jdbc:postgresql://localhost:5432/devtodo",
                "postgres",
                "admin123"
        );
    }

    @Test
    void search_fetchAllAndRetrieve_noExceptionThrown() {
        ILabelRepository repository = getRepository();
        List<ILabelRepository.DBLabel> list = null;
        try {
            list = repository.searchAll();
        } catch (ILabelRepository.DBException e) {
            fail(e);
        }

        for (ILabelRepository.DBLabel dbLabel : list) {
            assertDoesNotThrow(() -> repository.search(dbLabel.id()));
        }
    }
}