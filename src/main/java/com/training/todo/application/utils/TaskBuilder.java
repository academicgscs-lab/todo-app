package com.training.todo.application.utils;

import com.training.todo.domain.Task;
import com.training.todo.domain.label.Label;

import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

public class TaskBuilder {
    private String name;
    private String description;
    private Date dueDate;
    private Vector<Label> labels;

    private final HashMap<String, Label> labelsMap;

    public TaskBuilder(HashMap<String, Label> labelsMap) {
        this.labelsMap = labelsMap;
    }

    public void stepA(String name, String description, Date dueDate) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
    }

    public void stepB(Vector<String> labelIds) throws Exception {
        this.labels = handleLabel(labelIds);
    }

    public Task build() {
        return Task.builder()
                .id(UUIDGenerator.generateUUID())
                .title(name)
                .description(description)
                .labels(labels)
                .dueDate(dueDate)
                .build();
    }

    private Vector<Label> handleLabel(Vector<String> labels) throws Exception {
        Vector<Label> list = new Vector<>();

        if (labels.isEmpty()) {
            return list;
        }

        for (String label : labels) {
            if (!this.labelsMap.containsKey(label)) {
                throw new Exception(String.format("Label %s not found", label));
            } else {
                list.add(this.labelsMap.get(label));
            }
        }

        return list;
    }
}
