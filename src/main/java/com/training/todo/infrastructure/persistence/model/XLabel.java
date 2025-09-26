package com.training.todo.infrastructure.persistence.model;

import com.training.todo.domain.label.Label;
import com.training.todo.domain.label.LabelType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "label")
@NoArgsConstructor
public class XLabel {
    public static String HOME = "labels";

    private String id;
    private String name;
    private String description;
    private LabelType type;

    public static XLabel mapToXLabel(Label label) {
        XLabel xLabel = new XLabel();
        xLabel.setId(label.getId());
        xLabel.setName(label.getName());
        xLabel.setDescription(label.getDescription());
        xLabel.setType(label.getType());
        return xLabel;
    }

    public static Label mapToLabel(XLabel xLabel) {
        return Label.builder()
                .id(xLabel.getId())
                .name(xLabel.getName())
                .type(xLabel.getType())
                .build();
    }
}
