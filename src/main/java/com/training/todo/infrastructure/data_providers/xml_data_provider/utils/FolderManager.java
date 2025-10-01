package com.training.todo.infrastructure.data_providers.xml_data_provider.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FolderManager {
    private final String folderPath;

    public FolderManager(String folderPath) {
        this.folderPath = folderPath;
    }

    public Optional<List<Path>> listFiles() throws IOException {
        try (Stream<Path> files = Files.list(Paths.get(folderPath))) {
            return Optional.of(files.filter(Files::isRegularFile).collect(Collectors.toList()));
        }
    }

    public Path createFile( String fileName) {
        File file = new File(String.format("%s/%s.xml", folderPath, fileName));
        return file.toPath();
    }
}
