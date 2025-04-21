package fr.esgi.rent.beans;

public record RentalProperty(
        int referenceId,
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
