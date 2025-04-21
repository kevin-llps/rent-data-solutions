package fr.esgi.rent.mapper;

import fr.esgi.rent.beans.RentalProperty;
import fr.esgi.rent.dto.request.RentalPropertyRequestDto;
import fr.esgi.rent.dto.response.RentalPropertyResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Random;

import static fr.esgi.rent.beans.PropertyType.FLAT;
import static fr.esgi.rent.samples.RentalPropertyDtoSample.rentalPropertyDtoList;
import static fr.esgi.rent.samples.RentalPropertySample.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RentalPropertyDtoMapperTest {

    @InjectMocks
    private RentalPropertyDtoMapper rentalPropertyDtoMapper;

    @Mock
    private Random random;

    @Test
    void shouldMapToDtoList() {
        List<RentalProperty> rentalProperties = rentalProperties();
        List<RentalPropertyResponseDto> expectedRentalPropertyDtoList = rentalPropertyDtoList();

        List<RentalPropertyResponseDto> rentalPropertyDtoList = rentalPropertyDtoMapper.mapToDtoList(rentalProperties);

        assertThat(rentalPropertyDtoList).containsExactlyInAnyOrderElementsOf(expectedRentalPropertyDtoList);
    }

    @Test
    void shouldMapToDto() {
        RentalProperty rentalProperty = oneRentalProperty();

        RentalPropertyResponseDto rentalPropertyDto = rentalPropertyDtoMapper.mapToDto(rentalProperty);

        assertThat(rentalPropertyDto.description()).isEqualTo("Appartement spacieux avec vue sur l'ESGI");
        assertThat(rentalPropertyDto.address()).isEqualTo("77 Rue des roses");
        assertThat(rentalPropertyDto.town()).isEqualTo("Paris");
        assertThat(rentalPropertyDto.propertyType()).isEqualTo(FLAT);
        assertThat(rentalPropertyDto.rentAmount()).isEqualTo(750.90);
        assertThat(rentalPropertyDto.securityDepositAmount()).isEqualTo(1200.90);
        assertThat(rentalPropertyDto.area()).isEqualTo(37.48);
    }

    @Test
    void shouldMapToBean() {
        RentalPropertyRequestDto rentalPropertyRequestDto = oneRentalPropertyRequestDto();

        when(random.nextInt(Integer.MAX_VALUE)).thenReturn(12344);

        RentalProperty rentalProperty = rentalPropertyDtoMapper.mapToBean(rentalPropertyRequestDto);

        assertThat(rentalProperty.referenceId()).isEqualTo(12345);
        assertThat(rentalProperty.description()).isEqualTo("Appartement spacieux avec vue sur l'ESGI");
        assertThat(rentalProperty.town()).isEqualTo("Paris");
        assertThat(rentalProperty.address()).isEqualTo("77 Rue des roses");
        assertThat(rentalProperty.propertyType()).isEqualTo(FLAT);
        assertThat(rentalProperty.rentAmount()).isEqualTo(750.90);
        assertThat(rentalProperty.securityDepositAmount()).isEqualTo(1200.90);
        assertThat(rentalProperty.area()).isEqualTo(37.48);
        assertThat(rentalProperty.bedroomsCount()).isEqualTo(2);
        assertThat(rentalProperty.floorNumber()).isEqualTo(1);
        assertThat(rentalProperty.numberOfFloors()).isEqualTo(3);
        assertThat(rentalProperty.constructionYear()).isEqualTo(1990);
        assertThat(rentalProperty.hasElevator()).isTrue();
        assertThat(rentalProperty.hasIntercom()).isTrue();
        assertThat(rentalProperty.hasBalcony()).isTrue();
        assertThat(rentalProperty.hasParkingSpace()).isTrue();
    }

}
