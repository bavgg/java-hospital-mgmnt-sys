package com.example.hms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Overridesd
    public void start(Stage stage) throws IOException {
//        System.out.println(MainApplication.class.getResource("switch-views/doctors-view.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("dashboard-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}