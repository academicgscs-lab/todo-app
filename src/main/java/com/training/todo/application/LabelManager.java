package com.training.todo.application;

import com.training.todo.application.utils.UUIDGenerator;
import com.training.todo.domain.label.Label;
import com.training.todo.domain.label.LabelType;
import lombok.Getter;

import java.util.HashMap;

public class LabelManager {
    @Getter
    private final HashMap<String, Label> labelIdMap;
    @Getter
    private final HashMap<LabelType, Label> labelTypeMap;

    public LabelManager() {
        labelIdMap = new HashMap<>();
        labelTypeMap = new HashMap<>();
    }

    public Label addLabel(String name, String description, LabelType type) {
        Label label = Label.builder().
                id(UUIDGenerator.generateUUID())
                .name(name)
                .description(description)
                .type(type)
                .build();
        labelIdMap.put(label.getId(), label);
        labelTypeMap.put(label.getType(), label);
        return label;
    }
}
