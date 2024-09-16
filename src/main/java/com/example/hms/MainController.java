package com.example.hms;

import com.example.hms.models.Doctor;
import com.example.hms.repositories.DoctorRepository;
import com.example.hms.repositories.DoctoryRepositoryImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class MainController {

    @FXML
    private VBox mainIndex;

    @FXML
    private VBox switchContainer;

    @FXML
    private Button doctorsButton;

    @FXML
    private Button patientsButton;

    @FXML
    private Button appointmentsButton;

    @FXML
    private Button dashboardButton;

    private static final Logger logger = Logger.getLogger(MainController.class.getName());

    @FXML
    private void initialize() {
//        default view
        handleSwitchViews("index-view");

        doctorsButton.setOnAction(event -> handleSwitchViews("doctors-view"));
        patientsButton.setOnAction(event -> handleSwitchViews("patients-view"));
        appointmentsButton.setOnAction(event -> handleSwitchViews("appointments-view"));
        dashboardButton.setOnAction(event -> handleSwitchViews("index-view"));
    }



    private void handleSwitchViews(String view) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(String.format("/com/example/hms/switch-views/%s.fxml", view)));
            Parent doctorsView = fxmlLoader.load();

            switchContainer.getChildren().clear();
            switchContainer.getChildren().add(doctorsView);

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load doctors-view.fxml", e);
        }
    }
}
