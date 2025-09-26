package com.training.todo.domain.label;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

// is this the best strategy?
@Builder
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Label {
    @EqualsAndHashCode.Include
    private String id;
    private String name;
    private String description;
    private LabelType type;
}
