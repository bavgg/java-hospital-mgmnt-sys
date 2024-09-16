module com.example.hms {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires java.desktop;

    opens com.example.hms to javafx.fxml;
    opens com.example.hms.controllers to javafx.fxml;
    opens com.example.hms.models to javafx.base;


    exports com.example.hms;
    exports com.example.hms.models;
    exports com.example.hms.controllers;
}