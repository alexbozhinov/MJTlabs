package bg.sofia.uni.fmi.mjt.lab5.simcity.exceptions;

public class BuildableAlreadyExistsException extends RuntimeException {
    public BuildableAlreadyExistsException(String message) {
        super(message);
    }

    public BuildableAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
