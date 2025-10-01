package com.training.todo.infrastructure.data_providers.xml_data_provider;

import com.training.todo.core.usecases.repository.ILabelRepository;
import com.training.todo.infrastructure.data_providers.xml_data_provider.model.XLabel;
import com.training.todo.infrastructure.data_providers.xml_data_provider.utils.FolderManager;
import com.training.todo.infrastructure.data_providers.xml_data_provider.utils.XmlFacade;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class XmlLabelRepository implements ILabelRepository {
    private final FolderManager folderManager;
    private final XmlFacade<XLabel> xmlHandler;
    private final HashMap<String, DBLabel> data;

    public XmlLabelRepository(FolderManager folderManager, XmlFacade<XLabel> xmlHandler) {
        this.folderManager = folderManager;
        this.xmlHandler = xmlHandler;
        data = new HashMap<>();
    }

    @Override
    public Optional<DBLabel> search(String id) throws DBException {
        try {
            loadData();
        } catch (DBException e) {
            throw new DBException(e.getMessage(), e);
        }
        return Optional.empty();
    }

    private List<Path> readPaths() throws DBException {
        List<Path> files;
        try {
            files = folderManager.listFiles()
                    .orElseThrow(() -> new DBException("Couldn't  read files in path"));
        } catch (IOException e) {
            throw new DBException(e.getMessage(), e);
        }
        Set<String> recordedValues = data.keySet();
        return files.stream().filter(file -> !recordedValues.contains(file.toString())).collect(Collectors.toList());
    }

    private void loadData() throws DBException {
        for (Path path : readPaths()) {
            XLabel item;
            try {
                item = xmlHandler.unmarshal(path);
            } catch (JAXBException e) {
                throw new DBException(e.getMessage(), e);
            }
            if (!item.isDeleted()) {
                data.put(item.getId(),
                        ILabelRepository.DBLabel.builder()
                                .id(item.getId())
                                .title(item.getTitle())
                                .description(item.getDescription())
                                .build()
                );
            }
        }
    }
}
