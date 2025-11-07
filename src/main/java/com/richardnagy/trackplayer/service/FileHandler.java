package com.richardnagy.trackplayer.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileHandler {
    public static void writeSavedTracks(ArrayList<Path> trackPaths) {
        try (BufferedWriter myWriter = new BufferedWriter(new FileWriter("savedTracks.txt"))) {
            for (Path trackPath : trackPaths) {
                myWriter.write(trackPath.toString());
                myWriter.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
