package com.training.tomcatdemosample.application;

import com.training.tomcatdemosample.application.utils.UUIDGenerator;
import com.training.tomcatdemosample.domain.label.Label;
import lombok.Getter;

import java.util.HashMap;

public class LabelManager {
    @Getter
    private final HashMap<String, Label> labelMap;

    public LabelManager() {
        labelMap = new HashMap<>();
    }

    public void addLabel(String name, String description) {
        Label label = Label.builder().id(UUIDGenerator.generateUUID())
                .name(name).description(description).build();
        labelMap.put(label.getId(), label);
    }
}
