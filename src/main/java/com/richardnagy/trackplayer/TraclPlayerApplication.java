package com.richardnagy.trackplayer;

import static com.richardnagy.trackplayer.service.FileHandler.writeSavedTracks;
import static com.richardnagy.trackplayer.service.TrackScanner.findTracks;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TraclPlayerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TraclPlayerApplication.class.getResource("trackPlayer-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Track Player");
        stage.setScene(scene);
        stage.show();

        writeSavedTracks(findTracks());
    }
}
