package com.training.todo.infrastructure.persistence.helpers;

import com.training.todo.domain.label.Label;
import com.training.todo.infrastructure.persistence.model.XLabel;
import com.training.todo.infrastructure.persistence.utils.FileManager;
import com.training.todo.infrastructure.persistence.utils.XmlFacade;
import jakarta.xml.bind.JAXBException;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;
import java.util.Vector;

public class LabelPersistenceHelper extends PersistenceHelper<Label, XLabel> {

    public LabelPersistenceHelper(String homePath) {
        super(homePath, new XmlFacade<>(XLabel.class));
    }

    @Override
    public Optional<Collection<Label>> read() {
        Optional<Vector<Path>> optionPaths = FileManager.listFiles(String.format("%s/%s", homePath, XLabel.HOME));
        if (optionPaths.isEmpty()) {
            return Optional.empty();
        }

        Vector<Label> list = new Vector<>();
        for (Path path : optionPaths.get()) {
            try {
                Label task = XLabel.mapToLabel(xml.unmarshal(path));
                list.add(task);
            } catch (JAXBException e) {
                System.out.println(e.getMessage());
            }
        }

        return Optional.of(list);
    }

    @Override
    public boolean write(Collection<Label> list) {
        for (Label item : list) {
            try {
                xml.marshall(XLabel.mapToXLabel(item), FileManager.createFile(homePath, XLabel.HOME, item.getId()));
            } catch (JAXBException e) {
                System.out.println(e.getMessage());
            }
        }
        return true;
    }
}
