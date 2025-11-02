package com.richardnagy.trackplayer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class TrackScanner {
    static ArrayList<String> findTracks() {
        Path startPath = Paths.get("C:\\Users\\bigri\\Music");
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
