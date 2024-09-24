package com.example.hms.repositories;

import com.example.hms.models.Doctor;
import com.example.hms.models.Patient;

import javax.print.Doc;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DoctorRepository {
    Optional<Doctor> findById(int doctorId);
    List<Doctor> findAll();
    void save(Doctor doctor);
    void update(Doctor doctor);
    void delete(int doctorId);
}
