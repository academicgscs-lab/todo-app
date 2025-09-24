package com.training.todo.domain;

import com.training.todo.domain.label.Label;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.Vector;

@Builder
public class Task {
    @Getter
    private String id;
    private String title;
    private String description;

    private Vector<Label> labels;

    private Date dueDate;
    private Date startDate;
}
