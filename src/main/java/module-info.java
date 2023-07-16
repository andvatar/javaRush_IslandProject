module com.example.islandproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.google.common;

    opens com.example.islandproject to javafx.fxml;
    exports com.example.islandproject;
}