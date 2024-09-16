package com.example.hms.repositories;

import com.example.hms.models.Doctor;

import javax.print.Doc;
import java.sql.SQLException;
import java.util.List;

public interface DoctorRepository {
    void create(Doctor appointment) throws SQLException;

    Doctor findById(int appointmentId) throws SQLException;

    List<Doctor> findAll() throws SQLException;

    Doctor update(Doctor appointment) throws SQLException;

    void delete(int appointmentId) throws SQLException;

}
