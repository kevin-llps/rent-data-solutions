package fr.esgi.rent.services;

import fr.esgi.rent.beans.EnergyClassification;
import fr.esgi.rent.beans.PropertyType;
import fr.esgi.rent.beans.RentalProperty;
import fr.esgi.rent.exception.MySQLDriverNotFoundException;
import fr.esgi.rent.exception.RentalPropertyDatabaseException;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RentalPropertyDatabaseService {

    private static final String DB_URL = System.getenv("DB_URL");
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

    public List<RentalProperty> getAllRentalProperties() {
        List<RentalProperty> rentalProperties = new ArrayList<>();
        String query = "SELECT * FROM rental_property";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    rentalProperties.add(mapResultSetToRentalProperty(resultSet));
                }
            }
        } catch (ClassNotFoundException e) {
            throw new MySQLDriverNotFoundException("MySQL JDBC Driver not found", e);
        } catch (SQLException e) {
            throw new RentalPropertyDatabaseException("Error fetching rental properties from database", e);
        }

        return rentalProperties;
    }

    public Optional<RentalProperty> getRentalPropertyById(int id) {
        String query = "SELECT * FROM rental_property WHERE reference_id = ?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return Optional.of(mapResultSetToRentalProperty(resultSet));
                }
                return Optional.empty();
            }
        } catch (ClassNotFoundException e) {
            throw new MySQLDriverNotFoundException("MySQL JDBC Driver not found", e);
        } catch (SQLException e) {
            throw new RentalPropertyDatabaseException("Error fetching rental property by ID from database", e);
        }
    }

    public void addRentalProperty(RentalProperty rentalProperty) {
        // TODO: Exercice 3
    }

    private RentalProperty mapResultSetToRentalProperty(ResultSet resultSet) throws SQLException {
        return new RentalProperty(
                resultSet.getInt("reference_id"),
                resultSet.getString("description"),
                resultSet.getString("town"),
                resultSet.getString("address"),
                PropertyType.getByDesignation(resultSet.getString("property_type")).orElse(null),
                resultSet.getDouble("rent_amount"),
                resultSet.getDouble("security_deposit_amount"),
                resultSet.getDouble("area"),
                resultSet.getInt("bedrooms_count"),
                resultSet.getInt("floor_number"),
                resultSet.getInt("number_of_floors"),
                resultSet.getInt("construction_year"),
                EnergyClassification.getByName(resultSet.getString("energy_classification")).orElse(null),
                resultSet.getBoolean("has_elevator"),
                resultSet.getBoolean("has_intercom"),
                resultSet.getBoolean("has_balcony"),
                resultSet.getBoolean("has_parking_space")
        );
    }

}
