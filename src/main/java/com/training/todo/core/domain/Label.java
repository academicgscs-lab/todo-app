package com.training.todo.core.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Label {
    @EqualsAndHashCode.Include
    private String id;
    private String title;
    private String description;
    private LabelType type;
}
