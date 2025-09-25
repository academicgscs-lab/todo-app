package com.training.todo.controller.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LabelDto {
    private String id;
    private String name;
    private String description;
}
