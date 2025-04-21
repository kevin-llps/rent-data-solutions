package fr.esgi.rent.api;

import fr.esgi.rent.beans.RentalProperty;
import fr.esgi.rent.dto.request.RentalPropertyRequestDto;
import fr.esgi.rent.dto.response.RentalPropertyResponseDto;
import fr.esgi.rent.exception.NotFoundRentalPropertyException;
import fr.esgi.rent.mapper.RentalPropertyDtoMapper;
import fr.esgi.rent.services.RentalPropertyDatabaseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static fr.esgi.rent.beans.EnergyClassification.D;
import static fr.esgi.rent.beans.PropertyType.FLAT;
import static fr.esgi.rent.samples.RentalPropertyDtoSample.oneRentalPropertyDto;
import static fr.esgi.rent.samples.RentalPropertyDtoSample.rentalPropertyDtoList;
import static fr.esgi.rent.samples.RentalPropertySample.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RentalPropertyResourceTest {

    @InjectMocks
    private RentalPropertyResource rentalPropertyResource;

    @Mock
    private RentalPropertyDatabaseService rentalPropertyDatabaseService;

    @Mock
    private RentalPropertyDtoMapper rentalPropertyDtoMapper;

    @Test
    void shouldGetRentalProperties() {
        List<RentalPropertyResponseDto> expectedRentalPropertyDtoList = rentalPropertyDtoList();
        List<RentalProperty> rentalProperties = rentalProperties();

        when(rentalPropertyDatabaseService.getAllRentalProperties()).thenReturn(rentalProperties);
        when(rentalPropertyDtoMapper.mapToDtoList(rentalProperties)).thenReturn(expectedRentalPropertyDtoList);

        List<RentalPropertyResponseDto> rentalPropertyDtoList = rentalPropertyResource.getRentalProperties();

        assertThat(rentalPropertyDtoList).containsExactlyInAnyOrderElementsOf(expectedRentalPropertyDtoList);

        verify(rentalPropertyDatabaseService).getAllRentalProperties();
        verify(rentalPropertyDtoMapper).mapToDtoList(rentalProperties);

        verifyNoMoreInteractions(rentalPropertyDatabaseService, rentalPropertyDtoMapper);
    }

    @Test
    void shouldGetRentalProperty() {
        RentalProperty rentalProperty = rentalProperty();
        RentalPropertyResponseDto expectedRentalPropertyDto = oneRentalPropertyDto();

        when(rentalPropertyDatabaseService.getRentalPropertyById(46890)).thenReturn(Optional.of(rentalProperty));
        when(rentalPropertyDtoMapper.mapToDto(rentalProperty)).thenReturn(expectedRentalPropertyDto);

        RentalPropertyResponseDto rentalPropertyResponseDto = rentalPropertyResource.getRentalProperty(46890);

        assertThat(rentalPropertyResponseDto).isEqualTo(expectedRentalPropertyDto);

        verify(rentalPropertyDatabaseService).getRentalPropertyById(46890);
        verify(rentalPropertyDtoMapper).mapToDto(rentalProperty);

        verifyNoMoreInteractions(rentalPropertyDatabaseService, rentalPropertyDtoMapper);
    }

    @Test
    void givenNonExistentId_shouldThrowNotFoundRentalPropertyException() {
        when(rentalPropertyDatabaseService.getRentalPropertyById(4690)).thenReturn(Optional.empty());

        assertThatExceptionOfType(NotFoundRentalPropertyException.class)
                .isThrownBy(() -> rentalPropertyResource.getRentalProperty(4690))
                .satisfies(e -> assertThat(e.getMessage()).isEqualTo("RentalProperty not found with id 4690"));

        verify(rentalPropertyDatabaseService).getRentalPropertyById(4690);

        verifyNoInteractions(rentalPropertyDtoMapper);
        verifyNoMoreInteractions(rentalPropertyDatabaseService);
    }

    @Test
    void shouldAddRentalProperty() {
        RentalPropertyRequestDto rentalPropertyRequestDto = oneRentalPropertyRequestDto();
        RentalProperty rentalProperty = oneRentalProperty();
        RentalPropertyResponseDto expectedRentalPropertyDto = oneRentalPropertyDto();

        when(rentalPropertyDtoMapper.mapToBean(rentalPropertyRequestDto)).thenReturn(rentalProperty);
        when(rentalPropertyDtoMapper.mapToDto(rentalProperty)).thenReturn(expectedRentalPropertyDto);

        RentalPropertyResponseDto rentalPropertyResponseDto = rentalPropertyResource.addRentalProperty(rentalPropertyRequestDto);

        assertThat(rentalPropertyResponseDto).isEqualTo(expectedRentalPropertyDto);

        verify(rentalPropertyDtoMapper).mapToBean(rentalPropertyRequestDto);
        verify(rentalPropertyDtoMapper).mapToDto(rentalProperty);
        verify(rentalPropertyDatabaseService).addRentalProperty(rentalProperty);

        verifyNoMoreInteractions(rentalPropertyDtoMapper, rentalPropertyDatabaseService);
    }

    private RentalProperty rentalProperty() {
        return new RentalProperty(
                46890,
                "Appartement spacieux avec vue sur l'ESGI",
                "Paris",
                "77 Rue des roses",
                FLAT,
                750.90,
                1200.90,
                37.48,
                2,
                1,
                3,
                1990,
                D,
                false,
                false,
                true,
                false);
    }

}
