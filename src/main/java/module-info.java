module com.richardnagy.trackplayer {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.richardnagy.trackplayer to javafx.fxml;
    exports com.richardnagy.trackplayer;
}