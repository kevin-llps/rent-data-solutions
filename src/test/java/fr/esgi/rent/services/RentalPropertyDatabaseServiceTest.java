package fr.esgi.rent.services;

import fr.esgi.rent.beans.EnergyClassification;
import fr.esgi.rent.beans.PropertyType;
import fr.esgi.rent.beans.RentalProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RentalPropertyDatabaseServiceTest {

    private RentalPropertyDatabaseService rentalPropertyDatabaseService;

    @BeforeEach
    void setUp() {
        rentalPropertyDatabaseService = new RentalPropertyDatabaseService();
    }

    @Test
    void shouldGetAllRentalProperties() throws Exception {
        try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
            Connection mockConnection = mock(Connection.class);
            Statement mockStatement = mock(Statement.class);
            ResultSet mockResultSet = mock(ResultSet.class);

            mockedDriverManager.when(() -> DriverManager.getConnection(any(), any(), any()))
                    .thenReturn(mockConnection);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery("SELECT * FROM rental_property")).thenReturn(mockResultSet);

            when(mockResultSet.next()).thenReturn(true, false);
            when(mockResultSet.getInt("reference_id")).thenReturn(1);
            when(mockResultSet.getString("description")).thenReturn("Test Description");
            when(mockResultSet.getString("town")).thenReturn("Test Town");
            when(mockResultSet.getString("address")).thenReturn("Test Address");
            when(mockResultSet.getString("property_type")).thenReturn(PropertyType.FLAT.getDesignation());
            when(mockResultSet.getDouble("rent_amount")).thenReturn(1000.0);
            when(mockResultSet.getDouble("security_deposit_amount")).thenReturn(2000.0);
            when(mockResultSet.getDouble("area")).thenReturn(50.0);
            when(mockResultSet.getInt("bedrooms_count")).thenReturn(2);
            when(mockResultSet.getInt("floor_number")).thenReturn(1);
            when(mockResultSet.getInt("number_of_floors")).thenReturn(3);
            when(mockResultSet.getInt("construction_year")).thenReturn(2000);
            when(mockResultSet.getString("energy_classification")).thenReturn("D");
            when(mockResultSet.getBoolean("has_elevator")).thenReturn(true);
            when(mockResultSet.getBoolean("has_intercom")).thenReturn(false);
            when(mockResultSet.getBoolean("has_balcony")).thenReturn(true);
            when(mockResultSet.getBoolean("has_parking_space")).thenReturn(false);

            List<RentalProperty> rentalProperties = rentalPropertyDatabaseService.getAllRentalProperties();

            assertThat(rentalProperties).hasSize(1);
            RentalProperty rentalProperty = rentalProperties.get(0);
            assertThat(rentalProperty.referenceId()).isEqualTo(1);
            assertThat(rentalProperty.description()).isEqualTo("Test Description");
            assertThat(rentalProperty.town()).isEqualTo("Test Town");
            assertThat(rentalProperty.address()).isEqualTo("Test Address");
            assertThat(rentalProperty.propertyType()).isEqualTo(PropertyType.FLAT);
            assertThat(rentalProperty.rentAmount()).isEqualTo(1000.0);
            assertThat(rentalProperty.securityDepositAmount()).isEqualTo(2000.0);
            assertThat(rentalProperty.area()).isEqualTo(50.0);
            assertThat(rentalProperty.bedroomsCount()).isEqualTo(2);
            assertThat(rentalProperty.floorNumber()).isEqualTo(1);
            assertThat(rentalProperty.numberOfFloors()).isEqualTo(3);
            assertThat(rentalProperty.constructionYear()).isEqualTo(2000);
            assertThat(rentalProperty.energyClassification()).isEqualTo(EnergyClassification.D);
            assertThat(rentalProperty.hasElevator()).isTrue();
            assertThat(rentalProperty.hasIntercom()).isFalse();
            assertThat(rentalProperty.hasBalcony()).isTrue();
            assertThat(rentalProperty.hasParkingSpace()).isFalse();

            verify(mockConnection).createStatement();
            verify(mockStatement).executeQuery("SELECT * FROM rental_property");
            verify(mockResultSet, times(2)).next();
            verify(mockConnection).close();
            verify(mockStatement).close();

            verifyNoMoreInteractions(mockConnection, mockStatement);
        }
    }

    @Test
    void shouldGetRentalPropertyById() throws Exception {
        try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
            Connection mockConnection = mock(Connection.class);
            PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
            ResultSet mockResultSet = mock(ResultSet.class);

            mockedDriverManager.when(() -> DriverManager.getConnection(any(), any(), any()))
                    .thenReturn(mockConnection);
            when(mockConnection.prepareStatement("SELECT * FROM rental_property WHERE reference_id = ?"))
                    .thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt("reference_id")).thenReturn(1);
            when(mockResultSet.getString("description")).thenReturn("Test Description");
            when(mockResultSet.getString("town")).thenReturn("Test Town");
            when(mockResultSet.getString("address")).thenReturn("Test Address");
            when(mockResultSet.getString("property_type")).thenReturn(PropertyType.FLAT.getDesignation());
            when(mockResultSet.getDouble("rent_amount")).thenReturn(1000.0);
            when(mockResultSet.getDouble("security_deposit_amount")).thenReturn(2000.0);
            when(mockResultSet.getDouble("area")).thenReturn(50.0);
            when(mockResultSet.getInt("bedrooms_count")).thenReturn(2);
            when(mockResultSet.getInt("floor_number")).thenReturn(1);
            when(mockResultSet.getInt("number_of_floors")).thenReturn(3);
            when(mockResultSet.getInt("construction_year")).thenReturn(2000);
            when(mockResultSet.getString("energy_classification")).thenReturn("D");
            when(mockResultSet.getBoolean("has_elevator")).thenReturn(true);
            when(mockResultSet.getBoolean("has_intercom")).thenReturn(false);
            when(mockResultSet.getBoolean("has_balcony")).thenReturn(true);
            when(mockResultSet.getBoolean("has_parking_space")).thenReturn(false);

            Optional<RentalProperty> actual = rentalPropertyDatabaseService.getRentalPropertyById(1);

            assertThat(actual).isPresent();

            RentalProperty rentalProperty = actual.get();
            assertThat(rentalProperty.referenceId()).isEqualTo(1);
            assertThat(rentalProperty.description()).isEqualTo("Test Description");
            assertThat(rentalProperty.town()).isEqualTo("Test Town");
            assertThat(rentalProperty.address()).isEqualTo("Test Address");
            assertThat(rentalProperty.propertyType()).isEqualTo(PropertyType.FLAT);
            assertThat(rentalProperty.rentAmount()).isEqualTo(1000.0);
            assertThat(rentalProperty.securityDepositAmount()).isEqualTo(2000.0);
            assertThat(rentalProperty.area()).isEqualTo(50.0);
            assertThat(rentalProperty.bedroomsCount()).isEqualTo(2);
            assertThat(rentalProperty.floorNumber()).isEqualTo(1);
            assertThat(rentalProperty.numberOfFloors()).isEqualTo(3);
            assertThat(rentalProperty.constructionYear()).isEqualTo(2000);
            assertThat(rentalProperty.energyClassification()).isEqualTo(EnergyClassification.D);
            assertThat(rentalProperty.hasElevator()).isTrue();
            assertThat(rentalProperty.hasIntercom()).isFalse();
            assertThat(rentalProperty.hasBalcony()).isTrue();
            assertThat(rentalProperty.hasParkingSpace()).isFalse();

            verify(mockConnection).prepareStatement("SELECT * FROM rental_property WHERE reference_id = ?");
            verify(mockPreparedStatement).setInt(1, 1);
            verify(mockPreparedStatement).executeQuery();
            verify(mockConnection).close();
            verify(mockPreparedStatement).close();

            verifyNoMoreInteractions(mockConnection, mockPreparedStatement);
        }
    }

    @Test
    void shouldReturnEmptyWhenRentalPropertyNotFoundById() throws Exception {
        try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
            Connection mockConnection = mock(Connection.class);
            PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
            ResultSet mockResultSet = mock(ResultSet.class);

            mockedDriverManager.when(() -> DriverManager.getConnection(any(), any(), any()))
                    .thenReturn(mockConnection);
            when(mockConnection.prepareStatement("SELECT * FROM rental_property WHERE reference_id = ?"))
                    .thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

            when(mockResultSet.next()).thenReturn(false);

            Optional<RentalProperty> rentalProperty = rentalPropertyDatabaseService.getRentalPropertyById(1);

            assertThat(rentalProperty).isEmpty();

            verify(mockConnection).prepareStatement("SELECT * FROM rental_property WHERE reference_id = ?");
            verify(mockPreparedStatement).setInt(1, 1);
            verify(mockPreparedStatement).executeQuery();
            verify(mockConnection).close();
            verify(mockPreparedStatement).close();

            verifyNoMoreInteractions(mockConnection, mockPreparedStatement);
        }
    }

    @Test
    void shouldAddRentalProperty() throws Exception {
        try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
            Connection mockConnection = mock(Connection.class);
            PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

            mockedDriverManager.when(() -> DriverManager.getConnection(any(), any(), any()))
                    .thenReturn(mockConnection);
            when(mockConnection.prepareStatement("INSERT INTO rental_property (reference_id, description, town, address, property_type, rent_amount, " +
                    "security_deposit_amount, area, bedrooms_count, floor_number, number_of_floors, construction_year, " +
                    "energy_classification, has_elevator, has_intercom, has_balcony, has_parking_space) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"))
                    .thenReturn(mockPreparedStatement);

            RentalProperty rentalProperty = new RentalProperty(
                    1, "Test Description", "Test Town", "Test Address", PropertyType.FLAT, 1000.0, 2000.0, 50.0, 2, 1, 3, 2000,
                    EnergyClassification.D, true, false, true, false
            );

            rentalPropertyDatabaseService.addRentalProperty(rentalProperty);

            verify(mockPreparedStatement).setInt(1, rentalProperty.referenceId());
            verify(mockPreparedStatement).setString(2, rentalProperty.description());
            verify(mockPreparedStatement).setString(3, rentalProperty.town());
            verify(mockPreparedStatement).setString(4, rentalProperty.address());
            verify(mockPreparedStatement).setString(5, rentalProperty.propertyType().getDesignation());
            verify(mockPreparedStatement).setDouble(6, rentalProperty.rentAmount());
            verify(mockPreparedStatement).setDouble(7, rentalProperty.securityDepositAmount());
            verify(mockPreparedStatement).setDouble(8, rentalProperty.area());
            verify(mockPreparedStatement).setInt(9, rentalProperty.bedroomsCount());
            verify(mockPreparedStatement).setInt(10, rentalProperty.floorNumber());
            verify(mockPreparedStatement).setInt(11, rentalProperty.numberOfFloors());
            verify(mockPreparedStatement).setInt(12, rentalProperty.constructionYear());
            verify(mockPreparedStatement).setString(13, rentalProperty.energyClassification().name());
            verify(mockPreparedStatement).setBoolean(14, rentalProperty.hasElevator());
            verify(mockPreparedStatement).setBoolean(15, rentalProperty.hasIntercom());
            verify(mockPreparedStatement).setBoolean(16, rentalProperty.hasBalcony());
            verify(mockPreparedStatement).setBoolean(17, rentalProperty.hasParkingSpace());

            verify(mockPreparedStatement).executeUpdate();

            verify(mockPreparedStatement).close();

            verifyNoMoreInteractions(mockPreparedStatement);
        }
    }
}
