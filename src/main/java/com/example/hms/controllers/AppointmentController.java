package com.example.hms.controllers;

import com.example.hms.models.Appointment;
import com.example.hms.repositories.AppointmentRepository;
import com.example.hms.repositories.AppointmentRepositoryImpl;
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


public class AppointmentController {
    @FXML
    private TableView<Appointment> appointmentsTable;
    @FXML
    private TableColumn<Appointment, Integer> appointmentIdColumn;
    @FXML
    private TableColumn<Appointment, String> patientIdColumn;
    @FXML
    private TableColumn<Appointment, String> doctorIdColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentDateColumn;
    @FXML
    private TableColumn<Appointment, String> durationColumn;
    @FXML
    private TableColumn<Appointment, String> statusColumn;
    @FXML
    private TableColumn<Appointment, String> reasonColumn;
    @FXML
    private TableColumn<Appointment, Void> actionColumn;

    @FXML
    public void initialize() {
        // Set up the columns with the properties of the Appointment class
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        patientIdColumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        doctorIdColumn.setCellValueFactory(new PropertyValueFactory<>("doctorId"));
        appointmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));

        addActionButtonsToTable();
        // Populate the TableView with sample data
        appointmentsTable.setItems(getAppointmentList());
    }

    public ObservableList<Appointment> getAppointmentList() {
        AppointmentRepository appointmentRepository = new AppointmentRepositoryImpl();
        List<Appointment> appointmentList = appointmentRepository.findAll();

        return FXCollections.observableArrayList(appointmentList);
    }

    private void addActionButtonsToTable() {
        Callback<TableColumn<Appointment, Void>, TableCell<Appointment, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Appointment, Void> call(final TableColumn<Appointment, Void> param) {
                return new TableCell<>() {

                    private final Button editButton = new Button("Edit");
                    private final Button deleteButton = new Button("Delete");

                    {
                        editButton.setOnAction(event -> {
                            Appointment appointment = getTableView().getItems().get(getIndex());
                            editAppointment(appointment);
                        });

                        deleteButton.setOnAction(event -> {
                            Appointment appointment = getTableView().getItems().get(getIndex());
                            deleteAppointment(appointment);
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

    private void editAppointment(Appointment appointment) {
        System.out.println("Edit appointment: " + appointment);
    }

    private void deleteAppointment(Appointment appointment) {
        System.out.println("Delete appointment: " + appointment);
        appointmentsTable.getItems().remove(appointment);
    }

}
