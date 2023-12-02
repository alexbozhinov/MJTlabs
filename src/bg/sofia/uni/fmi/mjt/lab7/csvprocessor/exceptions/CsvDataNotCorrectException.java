package bg.sofia.uni.fmi.mjt.lab7.csvprocessor.exceptions;

public class CsvDataNotCorrectException extends Exception {
    public CsvDataNotCorrectException(String message) {
        super(message);
    }

    public CsvDataNotCorrectException(String message, Throwable cause) {
        super(message, cause);
    }
}
