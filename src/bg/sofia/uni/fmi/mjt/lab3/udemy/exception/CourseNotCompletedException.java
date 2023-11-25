package bg.sofia.uni.fmi.mjt.lab3.udemy.exception;

public class CourseNotCompletedException extends Exception {
    public CourseNotCompletedException(String message) {
        super(message);
    }

    public CourseNotCompletedException(String message, Throwable cause) {
        super(message, cause);
    }
}
