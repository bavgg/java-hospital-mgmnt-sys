package com.example.hms.repositories;

import com.example.hms.models.Doctor;
import com.example.hms.utils.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DoctorRepositoryImpl implements DoctorRepository {
    private final Connection connection;
    private static final Logger logger = Logger.getLogger(DoctorRepositoryImpl.class.getName());

    public DoctorRepositoryImpl() {
        try {
            this.connection = Database.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Doctor> findById(int doctorId) {
        String sql = "SELECT * FROM doctors WHERE doctor_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, doctorId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Doctor doctor = mapRowToDoctor(rs);
                    return Optional.of(doctor);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error finding doctor", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Doctor> findAll() {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctors";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Doctor doctor = mapRowToDoctor(rs);
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error finding doctors", e);
        }
        return doctors;
    }

    @Override
    public void save(Doctor doctor) {
        String sql = "INSERT INTO doctors (first_name, last_name, specialty, phone_number, email) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            setDoctorParameters(stmt, doctor);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error saving doctor", e);
        }
    }

    @Override
    public void update(Doctor doctor) {
        String sql = "UPDATE doctors SET first_name = ?, last_name = ?, specialty = ?, phone_number = ?, email = ? WHERE doctor_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            setDoctorParameters(stmt, doctor);
            stmt.setLong(6, doctor.getDoctorId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating doctor", e);
        }
    }

    @Override
    public void delete(int doctorId) {
        String sql = "DELETE FROM doctors WHERE doctor_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, doctorId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting doctor", e);
        }
    }

    private Doctor mapRowToDoctor(ResultSet rs) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setDoctorId(rs.getLong("doctor_id"));
        doctor.setFirstName(rs.getString("first_name"));
        doctor.setLastName(rs.getString("last_name"));
        doctor.setSpecialty(rs.getString("specialty"));
        doctor.setPhoneNumber(rs.getString("phone_number"));
        doctor.setEmail(rs.getString("email"));
        doctor.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        doctor.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return doctor;
    }

    private void setDoctorParameters(PreparedStatement stmt, Doctor doctor) throws SQLException {
        stmt.setString(1, doctor.getFirstName());
        stmt.setString(2, doctor.getLastName());
        stmt.setString(3, doctor.getSpecialty());
        stmt.setString(4, doctor.getPhoneNumber());
        stmt.setString(5, doctor.getEmail());
    }

}

