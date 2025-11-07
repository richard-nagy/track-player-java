module com.richardnagy.trackplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires jaudiotagger;

    opens com.richardnagy.trackplayer.controller to javafx.fxml;

    exports com.richardnagy.trackplayer;
}