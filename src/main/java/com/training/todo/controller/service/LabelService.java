package com.training.todo.controller.service;

import com.training.todo.application.LabelManager;
import com.training.todo.controller.model.LabelDto;
import com.training.todo.domain.label.Label;

import java.util.Collection;

public class LabelService {
    private final LabelManager labelManager;

    public LabelService(LabelManager labelManager) {
        this.labelManager = labelManager;
    }

    public Collection<LabelDto> getLabels(){
        return labelManager.getLabels().stream().map(LabelService::mapToDto).toList();
    }

    public LabelDto getLabel(String id){
        return mapToDto(labelManager.getLabel(id));
    }

    public static LabelDto mapToDto(Label label){
        return LabelDto.builder()
                .id(label.getId())
                .name(label.getName())
                .description(label.getDescription())
                .build();
    }
}
