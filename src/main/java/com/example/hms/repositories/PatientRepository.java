package com.example.hms.repositories;

import com.example.hms.models.Patient;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PatientRepository {
    Optional<Patient> findById(int patientId);
    List<Patient> findAll();
    void save(Patient patient);
    void update(Patient patient);
    void delete(int patientId);
}
