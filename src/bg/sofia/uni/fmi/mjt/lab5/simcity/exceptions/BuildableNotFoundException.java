package bg.sofia.uni.fmi.mjt.lab5.simcity.exceptions;

public class BuildableNotFoundException extends RuntimeException {
    public BuildableNotFoundException(String message) {
        super(message);
    }

    public BuildableNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
