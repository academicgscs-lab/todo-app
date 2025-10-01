package com.training.todo.core.domain;

import lombok.Builder;

@Builder
public record ReadLabel (String id, String title, String description) {
}
