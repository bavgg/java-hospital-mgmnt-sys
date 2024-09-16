package com.example.hms.repositories;

import com.example.hms.models.Appointment;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository {
    // Create a new appointment
    void save(Appointment appointment);

    // Retrieve an appointment by its ID
    Optional<Appointment> findById(int appointmentId);

    // Retrieve all appointments
    List<Appointment> findAll();

    // Update an existing appointment
    void update(Appointment appointment);

    // Delete an appointment by its ID
    void deleteById(int appointmentId);

}
