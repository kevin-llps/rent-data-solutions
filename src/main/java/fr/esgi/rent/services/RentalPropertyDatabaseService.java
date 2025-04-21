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
        String query = "INSERT INTO rental_properties (reference_id, description, town, address, property_type, rent_amount, " +
                       "security_deposit_amount, area, bedrooms_count, floor_number, number_of_floors, construction_year, " +
                       "energy_classification, has_elevator, has_intercom, has_balcony, has_parking_space) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, rentalProperty.referenceId());
            preparedStatement.setString(2, rentalProperty.description());
            preparedStatement.setString(3, rentalProperty.town());
            preparedStatement.setString(4, rentalProperty.address());
            preparedStatement.setString(5, rentalProperty.propertyType() != null ? rentalProperty.propertyType().getDesignation() : null);
            preparedStatement.setDouble(6, rentalProperty.rentAmount());
            preparedStatement.setDouble(7, rentalProperty.securityDepositAmount());
            preparedStatement.setDouble(8, rentalProperty.area());
            preparedStatement.setInt(9, rentalProperty.bedroomsCount());
            preparedStatement.setInt(10, rentalProperty.floorNumber());
            preparedStatement.setInt(11, rentalProperty.numberOfFloors());
            preparedStatement.setInt(12, rentalProperty.constructionYear());
            preparedStatement.setString(13, rentalProperty.energyClassification() != null ? rentalProperty.energyClassification().name() : null);
            preparedStatement.setBoolean(14, rentalProperty.hasElevator());
            preparedStatement.setBoolean(15, rentalProperty.hasIntercom());
            preparedStatement.setBoolean(16, rentalProperty.hasBalcony());
            preparedStatement.setBoolean(17, rentalProperty.hasParkingSpace());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error adding rental property to database", e);
        }
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
