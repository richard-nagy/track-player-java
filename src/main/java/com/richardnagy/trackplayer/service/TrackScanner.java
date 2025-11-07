package com.richardnagy.trackplayer.service;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.richardnagy.trackplayer.model.TrackType;

public class TrackScanner {
    public static ArrayList<String> findTracks(String path) {
        String escapedPath = path.replaceAll("\\\\", "\\\\\\\\\\\\");
        Path startPath = Paths.get(escapedPath);
        ArrayList<String> trackFilePaths = new ArrayList<>();

        Set<String> trackTypes = Arrays.stream(TrackType.values()).map(TrackType::name).collect(Collectors.toSet());
        try {
            Files.walkFileTree(startPath, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    Path fileName = file.getFileName();

                    if (fileName != null) {
                        String nameString = fileName.toString().toUpperCase();
                        int typeSeparatorIndex = nameString.lastIndexOf(".");

                        if (typeSeparatorIndex > 0) {
                            String trackType = nameString.substring(typeSeparatorIndex + 1);

                            if (trackTypes.contains(trackType)) {
                                trackFilePaths.add(file.toString());
                            }
                        }
                    }

                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            System.err.println("Error traversing directory: " + e.getMessage());
        }

        return trackFilePaths;
    }
}
