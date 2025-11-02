package com.richardnagy.trackplayer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileHandler {
    public static void writeSavedTracks(ArrayList<String> trackPaths) {
        try (BufferedWriter myWriter = new BufferedWriter(new FileWriter("savedTracks.txt"))) {
            for (String trackPath : trackPaths) {
                myWriter.write(trackPath);
                myWriter.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
