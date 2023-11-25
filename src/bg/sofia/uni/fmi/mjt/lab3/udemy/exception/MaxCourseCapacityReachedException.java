package bg.sofia.uni.fmi.mjt.lab3.udemy.exception;

public class MaxCourseCapacityReachedException extends Exception {
    public MaxCourseCapacityReachedException(String message) {
        super(message);
    }

    public MaxCourseCapacityReachedException(String message, Throwable cause) {
        super(message, cause);
    }
}
