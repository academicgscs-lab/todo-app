package com.training.todo.infrastructure.controller.mappers;

import com.training.todo.core.domain.Label;
import com.training.todo.core.domain.ReadLabel;
import com.training.todo.infrastructure.controller.model.LabelDto;

public class LabelDtoMapper {
    public static LabelDto toDto(ReadLabel readLabel){
        return LabelDto.builder()
                .id(readLabel.id())
                .description(readLabel.description())
                .title(readLabel.title())
                .build();
    }

    public static Label toEntity(LabelDto labelDto){
        return Label.builder()
                .id(labelDto.getId())
                .description(labelDto.getDescription())
                .title(labelDto.getTitle())
                .build();
    }
}
