package com.training.todo.infrastructure.data_providers.xml_data_provider.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class XTask {
    private String id;
    private String title;
    private String description;

    private String statusId;

    private long creationDateEpochDay;
    private long dueDateEpochDay;
    private long startDateEpochDay;

    @Setter
    private boolean isDeleted;

    public XTask(String id, String title, String description, String statusId, long creationDateEpochDay, long dueDateEpochDay, long startDateEpochDay) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.statusId = statusId;
        this.creationDateEpochDay = creationDateEpochDay;
        this.dueDateEpochDay = dueDateEpochDay;
        this.startDateEpochDay = startDateEpochDay;
        this.isDeleted = false;
    }
}
