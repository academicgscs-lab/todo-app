package com.training.todo.infrastructure.persistence.model;

import com.training.todo.application.LabelManager;
import com.training.todo.domain.Task;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class XTask {
    public static String HOME = "tasks";

    private String id;
    private String title;
    private String description;

    private String statusId;

    private long creationDateEpochDay;
    private long dueDateEpochDay;
    private long startDateEpochDay;

    public static XTask mapToXTask(Task task) {
        XTask xTask = new XTask();
        xTask.setId(task.getId());
        xTask.setTitle(task.getTitle());
        xTask.setDescription(task.getDescription());
        xTask.setStatusId(task.getStatus().getId());
        xTask.setCreationDateEpochDay(task.getCreationDate().toEpochDay());
        xTask.setDueDateEpochDay(task.getDueDate().toEpochDay());
        xTask.setStartDateEpochDay(task.getStartDate().toEpochDay());
        return xTask;
    }

    public static Task mapFromXTask(XTask xTask, LabelManager labelManager) {
        return Task.builder()
                .id(xTask.getId())
                .title(xTask.getTitle())
                .description(xTask.getDescription())
                .dueDate(LocalDate.ofEpochDay(xTask.getDueDateEpochDay()))
                .startDate(LocalDate.ofEpochDay(xTask.getStartDateEpochDay()))
                .creationDate(LocalDate.ofEpochDay(xTask.getCreationDateEpochDay()))
                .status(labelManager.getLabelIdMap().get(xTask.getStatusId()))
                .build();
    }
}
