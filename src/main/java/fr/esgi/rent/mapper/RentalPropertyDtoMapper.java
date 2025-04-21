package fr.esgi.rent.mapper;

import fr.esgi.rent.beans.RentalProperty;
import fr.esgi.rent.dto.request.RentalPropertyRequestDto;
import fr.esgi.rent.dto.response.RentalPropertyResponseDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Random;

@ApplicationScoped
public class RentalPropertyDtoMapper {

    private final Random random;

    public RentalPropertyDtoMapper() {
        this.random = new Random();
    }

    @Inject
    public RentalPropertyDtoMapper(Random random) {
        this.random = random;
    }

    public List<RentalPropertyResponseDto> mapToDtoList(List<RentalProperty> rentalProperties) {
        return rentalProperties.stream()
                .map(this::mapToDto)
                .toList();
    }

    public RentalPropertyResponseDto mapToDto(RentalProperty rentalProperty) {
        return new RentalPropertyResponseDto(
                rentalProperty.description(),
                rentalProperty.address(),
                rentalProperty.town(),
                rentalProperty.propertyType(),
                rentalProperty.rentAmount(),
                rentalProperty.securityDepositAmount(),
                rentalProperty.area());
    }

    public RentalProperty mapToBean(RentalPropertyRequestDto rentalPropertyRequestDto) {
        return new RentalProperty(
                getRandomInt(),
                rentalPropertyRequestDto.description(),
                rentalPropertyRequestDto.town(),
                rentalPropertyRequestDto.address(),
                rentalPropertyRequestDto.propertyType(),
                rentalPropertyRequestDto.rentAmount(),
                rentalPropertyRequestDto.securityDepositAmount(),
                rentalPropertyRequestDto.area(),
                rentalPropertyRequestDto.bedroomsCount(),
                rentalPropertyRequestDto.floorNumber(),
                rentalPropertyRequestDto.numberOfFloors(),
                rentalPropertyRequestDto.constructionYear(),
                rentalPropertyRequestDto.energyClassification(),
                rentalPropertyRequestDto.hasElevator(),
                rentalPropertyRequestDto.hasIntercom(),
                rentalPropertyRequestDto.hasBalcony(),
                rentalPropertyRequestDto.hasParkingSpace());
    }

    private int getRandomInt() {
        return random.nextInt(Integer.MAX_VALUE) + 1;
    }

}
