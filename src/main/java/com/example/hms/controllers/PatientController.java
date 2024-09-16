package com.example.hms.controllers;

import com.example.hms.models.Patient;
import com.example.hms.repositories.PatientRepository;
import com.example.hms.repositories.PatientRepositoryImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class PatientController {
    @FXML
    private TableView<Patient> patientsTable;
    @FXML private TableColumn<Patient, Integer> idColumn;
    @FXML private TableColumn<Patient, String> firstNameColumn;
    @FXML private TableColumn<Patient, String> lastNameColumn;
    @FXML private TableColumn<Patient, String> dobColumn;
    @FXML private TableColumn<Patient, String> genderColumn;
    @FXML private TableColumn<Patient, String> phoneNumberColumn;
    @FXML private TableColumn<Patient, String> emailColumn;
    @FXML private TableColumn<Patient, String> addressColumn;
    @FXML private TableColumn<Patient, String> emergencyContactNameColumn;
    @FXML private TableColumn<Patient, String> emergencyContactPhoneColumn;
    @FXML private TableColumn<Patient, String> insuranceProviderColumn;
    @FXML private TableColumn<Patient, String> insurancePolicyNumberColumn;
    @FXML private TableColumn<Patient, String> registrationDateColumn;

    @FXML
    private void initialize() {
        // Set up columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        emergencyContactNameColumn.setCellValueFactory(new PropertyValueFactory<>("emergencyContactName"));
        emergencyContactPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("emergencyContactPhone"));
        insuranceProviderColumn.setCellValueFactory(new PropertyValueFactory<>("insuranceProvider"));
        insurancePolicyNumberColumn.setCellValueFactory(new PropertyValueFactory<>("insurancePolicyNumber"));
        registrationDateColumn.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));

        patientsTable.setItems(getPatients());
    }

    private ObservableList<Patient> getPatients() {
        PatientRepository patientRepository = new PatientRepositoryImpl();
        List<Patient> patientList = patientRepository.findAll();
        return FXCollections.observableArrayList(patientList);
    }

}
