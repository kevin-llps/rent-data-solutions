package fr.esgi.rent.services;

import fr.esgi.rent.beans.EnergyClassification;
import fr.esgi.rent.beans.PropertyType;
import fr.esgi.rent.beans.RentalProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

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

}
