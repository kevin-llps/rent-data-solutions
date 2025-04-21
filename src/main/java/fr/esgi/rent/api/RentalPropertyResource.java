package fr.esgi.rent.api;

import fr.esgi.rent.beans.RentalProperty;
import fr.esgi.rent.dto.request.RentalPropertyRequestDto;
import fr.esgi.rent.dto.response.RentalPropertyResponseDto;
import fr.esgi.rent.exception.NotFoundRentalPropertyException;
import fr.esgi.rent.mapper.RentalPropertyDtoMapper;
import fr.esgi.rent.services.RentalPropertyDatabaseService;
import jakarta.inject.Inject;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.util.List;

@Path("/rental-properties")
public class RentalPropertyResource {

    private final RentalPropertyDatabaseService rentalPropertyDatabaseService;
    private final RentalPropertyDtoMapper rentalPropertyDtoMapper;

    @Inject
    public RentalPropertyResource(RentalPropertyDatabaseService rentalPropertyDatabaseService,
                                  RentalPropertyDtoMapper rentalPropertyDtoMapper) {
        this.rentalPropertyDatabaseService = rentalPropertyDatabaseService;
        this.rentalPropertyDtoMapper = rentalPropertyDtoMapper;
    }

    @GET
    public List<RentalPropertyResponseDto> getRentalProperties() {
        List<RentalProperty> rentalProperties = rentalPropertyDatabaseService.getAllRentalProperties();

        return rentalPropertyDtoMapper.mapToDtoList(rentalProperties);
    }

    @GET
    @Path("/{id}")
    public RentalPropertyResponseDto getRentalProperty(@PathParam("id") @Positive int id) {
        return rentalPropertyDatabaseService.getRentalPropertyById(id)
                .map(rentalPropertyDtoMapper::mapToDto)
                .orElseThrow(() -> new NotFoundRentalPropertyException("RentalProperty not found with id " + id));
    }

    @POST
    public RentalPropertyResponseDto addRentalProperty(RentalPropertyRequestDto rentalPropertyRequestDto) {
        RentalProperty rentalProperty = rentalPropertyDtoMapper.mapToBean(rentalPropertyRequestDto);

        rentalPropertyDatabaseService.addRentalProperty(rentalProperty);

        return rentalPropertyDtoMapper.mapToDto(rentalProperty);
    }
}