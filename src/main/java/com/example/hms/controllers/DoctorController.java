package com.example.hms.controllers;

import com.example.hms.models.Doctor;
import com.example.hms.repositories.DoctorRepository;
import com.example.hms.repositories.DoctoryRepositoryImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class DoctorController {
    @FXML
    private TableView<Doctor> doctorsTable;

    @FXML
    private TableColumn<Doctor, Integer> idColumn;
    @FXML
    private TableColumn<Doctor, String> firstNameColumn;
    @FXML
    private TableColumn<Doctor, String> lastNameColumn;
    @FXML
    private TableColumn<Doctor, String> specialtyColumn;
    @FXML
    private TableColumn<Doctor, String> phoneNumberColumn;
    @FXML
    private TableColumn<Doctor, String> emailColumn;
    @FXML
    private TableColumn<Doctor, String> hireDateColumn;
    @FXML
    private TableColumn<Doctor, Boolean> activeColumn;

    private final DoctorRepository doctorRepository = new DoctoryRepositoryImpl();

    @FXML
    public void initialize() {
        // Initialize the table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("doctorId"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        specialtyColumn.setCellValueFactory(new PropertyValueFactory<>("specialty"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        hireDateColumn.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
        activeColumn.setCellValueFactory(new PropertyValueFactory<>("active"));

        // Load the doctors data
        loadDoctors();
    }

    private void loadDoctors() {
        ObservableList<Doctor> doctors = null;
        try {
            doctors = FXCollections.observableArrayList(doctorRepository.findAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        doctorsTable.setItems(doctors);
    }
}
