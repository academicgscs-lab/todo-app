package com.training.todo.infrastructure.controller.service;

import com.training.todo.core.usecases.LabelManager;
import com.training.todo.infrastructure.controller.model.LabelDto;
import com.training.todo.core.domain.Label;

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
                .title(label.getTitle())
                .description(label.getDescription())
                .build();
    }
}
