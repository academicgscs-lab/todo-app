package com.training.todo.domain.label;

import lombok.Builder;
import lombok.Getter;

// is this the best strategy?
@Builder
public class Label {
    @Getter
    private String id;
    @Getter
    private String name;
    @Getter
    private String description;
}
