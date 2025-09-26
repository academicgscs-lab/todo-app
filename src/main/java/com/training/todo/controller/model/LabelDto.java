package com.training.todo.controller.model;

import com.training.todo.domain.label.Label;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LabelDto {
    private String id;
    private String name;
    private String description;

    public static LabelDto mapToDto(Label label){
        return LabelDto.builder()
                .id(label.getId())
                .name(label.getName())
                .description(label.getDescription())
                .build();
    }
}
