package com.example.hms.repositories;

import com.example.hms.MainController;
import com.example.hms.models.Patient;
import com.example.hms.utils.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PatientRepositoryImpl implements PatientRepository{
    private final Connection connection;

    private static final Logger logger = Logger.getLogger(PatientRepositoryImpl.class.getName());

    public PatientRepositoryImpl() {
        try {
            this.connection = Database.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Patient> findById(int patientId) {
        String sql = "SELECT * FROM patients WHERE patient_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, patientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRowToPatient(resultSet));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error finding patient with ID: " + patientId, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Patient> findAll() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                patients.add(mapRowToPatient(resultSet));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving all patients", e);
        }
        return patients;
    }

    @Override
    public void save(Patient patient) {
        String sql = "INSERT INTO patients (first_name, last_name, date_of_birth, gender, phone_number, email, address, emergency_contact_name, emergency_contact_phone, insurance_provider, insurance_policy_number, registration_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setPatientParameters(statement, patient);
            statement.setTimestamp(12, Timestamp.valueOf(patient.getRegistrationDate().atStartOfDay()));
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error saving patient: " + patient,  e);
        }
    }

    @Override
    public void update(Patient patient) {
        String sql = "UPDATE patients SET first_name = ?, last_name = ?, date_of_birth = ?, gender = ?, phone_number = ?, email = ?, address = ?, emergency_contact_name = ?, emergency_contact_phone = ?, insurance_provider = ?, insurance_policy_number = ? WHERE patient_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setPatientParameters(statement, patient);
            statement.setInt(12, patient.getPatientId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating patient: " + patient,  e);
        }
    }

    @Override
    public void delete(int patientId) {
        String sql = "DELETE FROM patients WHERE patient_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, patientId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting patient with ID: " + patientId,  e);
        }
    }

    private void setPatientParameters(PreparedStatement statement, Patient patient) throws SQLException {
        statement.setString(1, patient.getFirstName());
        statement.setString(2, patient.getLastName());
        statement.setDate(3, Date.valueOf(patient.getDateOfBirth()));
        statement.setString(4, patient.getGender());
        statement.setString(5, patient.getPhoneNumber());
        statement.setString(6, patient.getEmail());
        statement.setString(7, patient.getAddress());
        statement.setString(8, patient.getEmergencyContactName());
        statement.setString(9, patient.getEmergencyContactPhone());
        statement.setString(10, patient.getInsuranceProvider());
        statement.setString(11, patient.getInsurancePolicyNumber());
    }

    private Patient mapRowToPatient(ResultSet resultSet) throws SQLException {
        return new Patient(
                resultSet.getInt("patient_id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getDate("date_of_birth").toLocalDate(),
                resultSet.getString("gender"),
                resultSet.getString("phone_number"),
                resultSet.getString("email"),
                resultSet.getString("address"),
                resultSet.getString("emergency_contact_name"),
                resultSet.getString("emergency_contact_phone"),
                resultSet.getString("insurance_provider"),
                resultSet.getString("insurance_policy_number"),
                resultSet.getTimestamp("registration_date").toLocalDateTime().toLocalDate()
        );
    }
}
