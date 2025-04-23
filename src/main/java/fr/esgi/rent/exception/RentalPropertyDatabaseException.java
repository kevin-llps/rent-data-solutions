package fr.esgi.rent.exception;

public class RentalPropertyDatabaseException extends RuntimeException {
    public RentalPropertyDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
