package com.example.hms.repositories;

import com.example.hms.models.Doctor;
import com.example.hms.utils.Database;

import javax.print.Doc;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DoctoryRepositoryImpl implements DoctorRepository {
    @Override
    public void create(Doctor appointment) throws SQLException {

    }

    @Override
    public Doctor findById(int doctorID) throws SQLException {
        String sql = "SELECT * FROM doctors WHERE doctor_id=?";

        try (Connection conn = Database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                int doctor_id = rs.getInt("doctor_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String specialty = rs.getString("specialty");
                String phoneNumber = rs.getString("phone_number");
                String email = rs.getString("email");
                String hireDate = rs.getString("hire_date");
                boolean active = rs.getBoolean("active");

                return new Doctor(doctor_id, firstName, lastName, specialty, phoneNumber, email, hireDate, active);
            } else {
                return null;
            }
        }

    }

    @Override
    public List<Doctor> findAll() throws SQLException {
        List<Doctor> doctorList = new ArrayList<>();
        String sql = "SELECT * FROM doctors";
        try (Connection conn = Database.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int doctor_id = rs.getInt("doctor_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String specialty = rs.getString("specialty");
                String phoneNumber = rs.getString("phone_number");
                String email = rs.getString("email");
                String hireDate = rs.getString("hire_date");
                boolean active = rs.getBoolean("active");

                doctorList.add(new Doctor(doctor_id, firstName, lastName, specialty, phoneNumber, email, hireDate, active));
            }
        }
        return doctorList;
    }

    @Override
    public Doctor update(Doctor doctor) throws SQLException {
        return null;
    }

    @Override
    public void delete(int doctorID) throws SQLException {

    }
}

