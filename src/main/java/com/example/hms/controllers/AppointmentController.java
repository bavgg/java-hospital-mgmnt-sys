package com.example.hms.controllers;

import com.example.hms.models.Appointment;
import com.example.hms.repositories.AppointmentRepository;
import com.example.hms.repositories.AppointmentRepositoryImpl;
import com.example.hms.repositories.DoctorRepository;
import com.example.hms.repositories.DoctoryRepositoryImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    public void initialize() {
        // Set up the columns with the properties of the Appointment class
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        patientIdColumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        doctorIdColumn.setCellValueFactory(new PropertyValueFactory<>("doctorId"));
        appointmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));

        // Populate the TableView with sample data
        appointmentsTable.setItems(getAppointmentList());
    }

    public ObservableList<Appointment> getAppointmentList() {
        AppointmentRepository appointmentRepository = new AppointmentRepositoryImpl();
        List<Appointment> appointmentList = appointmentRepository.findAll();

        return FXCollections.observableArrayList(appointmentList);
    }
}
