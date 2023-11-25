package bg.sofia.uni.fmi.mjt.lab5.simcity.exceptions;

public class InsufficientPlotAreaException extends RuntimeException {
    public InsufficientPlotAreaException(String message) {
        super(message);
    }

    public InsufficientPlotAreaException(String message, Throwable cause) {
        super(message, cause);
    }
}
