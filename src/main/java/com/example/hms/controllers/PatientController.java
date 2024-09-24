package com.example.hms.controllers;

import com.example.hms.models.Patient;
import com.example.hms.repositories.PatientRepository;
import com.example.hms.repositories.PatientRepositoryImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.util.List;

public class PatientController {
    @FXML
    private TableView<Patient> patientsTable;
    @FXML private TableColumn<Patient, Void> actionColumn;
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

        addActionButtonsToTable();

        patientsTable.setItems(getPatients());
    }

    private ObservableList<Patient> getPatients() {
        PatientRepository patientRepository = new PatientRepositoryImpl();
        List<Patient> patientList = patientRepository.findAll();
        return FXCollections.observableArrayList(patientList);
    }

    private void addActionButtonsToTable() {
        Callback<TableColumn<Patient, Void>, TableCell<Patient, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Patient, Void> call(final TableColumn<Patient, Void> param) {
                return new TableCell<>() {

                    private final Button editButton = new Button("Edit");
                    private final Button deleteButton = new Button("Delete");

                    {
                        editButton.setOnAction(event -> {
                            Patient patient = getTableView().getItems().get(getIndex());
                            editAppointment(patient);
                        });

                        deleteButton.setOnAction(event -> {
                            Patient patient = getTableView().getItems().get(getIndex());
                            deleteAppointment(patient);
                        });

                        editButton.setStyle("-fx-background-color: lightgreen; -fx-padding: 5;");
                        deleteButton.setStyle("-fx-background-color: lightcoral; -fx-padding: 5;");
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(new HBox(10, editButton, deleteButton));
                        }
                    }
                };
            }
        };

        actionColumn.setCellFactory(cellFactory);
    }

    private void editAppointment(Patient patient) {
        System.out.println("Edit appointment: " + patient);
    }

    private void deleteAppointment(Patient patient) {
        System.out.println("Delete appointment: " + patient);
        patientsTable.getItems().remove(patient);
    }

}
