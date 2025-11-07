package com.richardnagy.trackplayer.controller;

import static com.richardnagy.trackplayer.service.FileHandler.writeSavedTracks;
import static com.richardnagy.trackplayer.service.TrackScanner.findTracks;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TrackPlayerController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField filePathInput;

    @FXML
    protected void fetchTracks() {
        String pathValue = filePathInput.getText();

        welcomeText.setText("Wrote track paths to the savedTracks.txt");
        writeSavedTracks(findTracks(pathValue));
    }
}
