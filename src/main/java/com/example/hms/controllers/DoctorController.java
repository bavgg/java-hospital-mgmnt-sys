package com.example.hms.controllers;

import com.example.hms.models.Doctor;
import com.example.hms.repositories.DoctorRepository;
import com.example.hms.repositories.DoctorRepositoryImpl;

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
    private TableColumn<Doctor, Void> actionColumn;

    @FXML
    public void initialize() {
        // Initialize the table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("doctorId"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        specialtyColumn.setCellValueFactory(new PropertyValueFactory<>("specialty"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));


        addActionButtonsToTable();
        // Load the doctors data
        doctorsTable.setItems(getDoctors());
    }

    private ObservableList<Doctor> getDoctors() {
        DoctorRepository doctorRepository = new DoctorRepositoryImpl();
        List<Doctor> doctorList = doctorRepository.findAll();
        return FXCollections.observableArrayList(doctorList);
    }

    private void addActionButtonsToTable() {
        Callback<TableColumn<Doctor, Void>, TableCell<Doctor, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Doctor, Void> call(final TableColumn<Doctor, Void> param) {
                return new TableCell<>() {

                    private final Button editButton = new Button("Edit");
                    private final Button deleteButton = new Button("Delete");

                    {
                        editButton.setOnAction(event -> {
                            Doctor doctor = getTableView().getItems().get(getIndex());
                            editDoctor(doctor);
                        });

                        deleteButton.setOnAction(event -> {
                            Doctor doctor = getTableView().getItems().get(getIndex());
                            deleteDoctor(doctor);
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

    private void editDoctor(Doctor doctor) {
        System.out.println("Edit appointment: " + doctor);
    }

    private void deleteDoctor(Doctor doctor) {
        System.out.println("Delete appointment: " + doctor);
        doctorsTable.getItems().remove(doctor);
    }

}
