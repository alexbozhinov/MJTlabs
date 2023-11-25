package bg.sofia.uni.fmi.mjt.lab3.udemy.exception;

import javax.naming.AuthenticationException;

public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
