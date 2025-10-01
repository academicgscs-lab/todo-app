package com.training.todo.core.usecases;

import com.training.todo.core.usecases.utils.UUIDGenerator;
import com.training.todo.core.domain.Label;
import com.training.todo.core.domain.LabelType;

import java.util.Collection;
import java.util.HashMap;

@Deprecated
public class LabelManager {
    private final HashMap<String, Label> labelIdMap;
    private final HashMap<LabelType, Label> labelTypeMap;

    public LabelManager() {
        labelIdMap = new HashMap<>();
        labelTypeMap = new HashMap<>();
    }

    public Label addLabel(String name, String description, LabelType type) {
        Label label = Label.builder().
                id(UUIDGenerator.generateUUID())
                .title(name)
                .description(description)
                .type(type)
                .build();
        labelIdMap.put(label.getId(), label);
        labelTypeMap.put(label.getType(), label);
        return label;
    }

    public Label getLabel(String id) {
        return  labelIdMap.get(id);
    }

    public Label getLabel(LabelType type) {
        return  labelTypeMap.get(type);
    }

    public Collection<Label> getLabels() {
        return labelIdMap.values();
    }

    public void appendLabelCollection(Collection<Label> labels) {
        labels.forEach(label -> labelIdMap.put(label.getId(), label));
        labels.forEach(label -> labelTypeMap.put(label.getType(), label));
    }
}
