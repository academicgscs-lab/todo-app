package com.training.todo.infrastructure.persistence.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileManager {
    public static Optional<Vector<Path>> listFiles(String dirPath){
        try (Stream<Path> files = Files.list(Paths.get(dirPath))) {
            Vector<Path> xmlFileNames = files
                    .filter(Files::isRegularFile) // Exclude directories
                    .collect(Collectors.toCollection(Vector::new));

            // TODO: throw exception
            if (xmlFileNames.isEmpty()) {
                System.out.println("No XML files found in the directory.");
            }
            return Optional.of(xmlFileNames);

        } catch (IOException e) {
            System.err.println("Error reading directory: " + e.getMessage());
        }

        return Optional.empty();
    }


    public static Path createFile(String home, String path, String fileName) {
        File file = new File(String.format("%s/%s/%s.xml", home, path, fileName));
        return file.toPath();
    }
}
