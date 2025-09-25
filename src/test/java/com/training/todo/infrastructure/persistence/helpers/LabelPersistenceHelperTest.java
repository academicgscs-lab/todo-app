package com.training.todo.infrastructure.persistence.helpers;

import com.training.todo.application.LabelManager;
import com.training.todo.domain.label.Label;
import com.training.todo.domain.label.LabelType;
import com.training.todo.infrastructure.persistence.model.XLabel;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LabelPersistenceHelperTest {

    @Test
    void write_storesObjects_Passes() {
        String home = String.format("%s/%s", System.getProperty("user.dir"), "persistence");
        LabelManager manager = new LabelManager();
        PersistenceHelper<Label, XLabel> helper = new LabelPersistenceHelper(home);
        manager.addLabel("Pending", "Hasn't been started", LabelType.PENDING);
        manager.addLabel("Completed", "Test", LabelType.COMPLETED);
        helper.write(new Vector<>(manager.getLabelIdMap().values()));
    }


    @Test
    void read_loadsObjects_Passes() {
        String home = String.format("%s/%s", System.getProperty("user.dir"), "persistence");
        LabelManager manager = new LabelManager();
        PersistenceHelper<Label, XLabel> helper = new LabelPersistenceHelper(home);
        Label controlLabel = manager.addLabel("Pending", "Hasn't been started", LabelType.PENDING);
        helper.write(new Vector<>(manager.getLabelIdMap().values()));

        Optional<Vector<Label>> result = helper.read();
        assertFalse(result.isEmpty());
        Vector<Label> labelList = result.get();
        assertFalse(labelList.isEmpty());

        boolean success = false;
        for (Label item : labelList) {
            if (item.getId().equals(controlLabel.getId()) && item.getType().equals(controlLabel.getType())) {
                success = true;
                break;
            }
        }
        assertTrue(success);
    }
}