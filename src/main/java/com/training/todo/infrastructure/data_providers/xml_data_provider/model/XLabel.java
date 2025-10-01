package com.training.todo.infrastructure.data_providers.xml_data_provider.model;

import com.training.todo.core.domain.LabelType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "labelList")
@NoArgsConstructor
public class XLabel {
    private String id;
    private String title;
    private String description;
    private LabelType type;
    @Setter
    private boolean isDeleted;

    public XLabel(String id, String title, String description, LabelType type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.isDeleted = false;
    }
}
