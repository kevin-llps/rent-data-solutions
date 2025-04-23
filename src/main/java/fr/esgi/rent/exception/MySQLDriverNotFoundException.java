package fr.esgi.rent.exception;

public class MySQLDriverNotFoundException extends RuntimeException {
    public MySQLDriverNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
