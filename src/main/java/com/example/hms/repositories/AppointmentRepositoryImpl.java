package com.example.hms.repositories;

import com.example.hms.models.Appointment;
import com.example.hms.utils.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppointmentRepositoryImpl implements AppointmentRepository {
    private Connection connection;

    private static final Logger logger = Logger.getLogger(AppointmentRepositoryImpl.class.getName());

    public AppointmentRepositoryImpl() {
        try {
            this.connection = Database.getConnection();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database connection error", e);
        }

    }

    @Override
    public void save(Appointment appointment) {
        String query = "INSERT INTO appointments (patient_id, doctor_id, appointment_date, duration, status, reason, created_at, updated_at) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, appointment.getPatientId());
            statement.setInt(2, appointment.getDoctorId());
            statement.setTimestamp(3, Timestamp.valueOf(appointment.getAppointmentDate()));
            statement.setInt(4, appointment.getDuration());
            statement.setString(5, appointment.getStatus());
            statement.setString(6, appointment.getReason());
            statement.setTimestamp(7, Timestamp.valueOf(appointment.getCreatedAt()));
            statement.setTimestamp(8, Timestamp.valueOf(appointment.getUpdatedAt()));

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error saving appointment", e);
        }
    }

    @Override
    public Optional<Appointment> findById(int appointmentId) {
        String query = "SELECT * FROM appointments WHERE appointment_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, appointmentId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                Appointment appointment = mapRowToAppointment(rs);
                return Optional.of(appointment);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error finding appointment", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Appointment> findAll() {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT * FROM appointments";

        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                appointments.add(mapRowToAppointment(rs));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error finding appointments", e);
        }

        return appointments;
    }

    @Override
    public void update(Appointment appointment) {
        String query = "UPDATE appointments SET patient_id = ?, doctor_id = ?, appointment_date = ?, duration = ?, " + "status = ?, reason = ?, updated_at = ? WHERE appointment_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, appointment.getPatientId());
            statement.setInt(2, appointment.getDoctorId());
            statement.setTimestamp(3, Timestamp.valueOf(appointment.getAppointmentDate()));
            statement.setInt(4, appointment.getDuration());
            statement.setString(5, appointment.getStatus());
            statement.setString(6, appointment.getReason());
            statement.setTimestamp(7, Timestamp.valueOf(appointment.getUpdatedAt()));
            statement.setInt(8, appointment.getAppointmentId());

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating appointment", e);
        }
    }

    @Override
    public void deleteById(int appointmentId) {
        String query = "DELETE FROM appointments WHERE appointment_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, appointmentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting appointment", e);
        }
    }

    // Helper method to map a row from ResultSet to an Appointment object
    private Appointment mapRowToAppointment(ResultSet rs) throws SQLException {
        return new Appointment(rs.getInt("appointment_id"), rs.getInt("patient_id"), rs.getInt("doctor_id"), rs.getTimestamp("appointment_date").toLocalDateTime(), rs.getInt("duration"), rs.getString("status"), rs.getString("reason"), rs.getTimestamp("created_at").toLocalDateTime(), rs.getTimestamp("updated_at").toLocalDateTime());
    }
}
