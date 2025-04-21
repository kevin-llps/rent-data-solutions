package fr.esgi.rent.dto.request;

import fr.esgi.rent.beans.EnergyClassification;
import fr.esgi.rent.beans.PropertyType;

public record RentalPropertyRequestDto(
        String description,
        String town,
        String address,
        PropertyType propertyType,
        double rentAmount,
        double securityDepositAmount,
        double area,
        int bedroomsCount,
        int floorNumber,
        int numberOfFloors,
        int constructionYear,
        EnergyClassification energyClassification,
        boolean hasElevator,
        boolean hasIntercom,
        boolean hasBalcony,
        boolean hasParkingSpace) {

}
