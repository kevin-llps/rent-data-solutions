package fr.esgi.rent.dto.response;

import fr.esgi.rent.beans.PropertyType;

public record RentalPropertyResponseDto(String description,
                                        String address,
                                        String town,
                                        PropertyType propertyType,
                                        double rentAmount,
                                        double securityDepositAmount,
                                        double area) {
}
