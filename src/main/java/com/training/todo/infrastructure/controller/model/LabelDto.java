package com.training.todo.infrastructure.controller.model;

import com.training.todo.core.domain.Label;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LabelDto {
    private String id;
    private String title;
    private String description;

    public static LabelDto mapToDto(Label label){
        return LabelDto.builder()
                .id(label.getId())
                .title(label.getTitle())
                .description(label.getDescription())
                .build();
    }
}
