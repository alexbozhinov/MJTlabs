package bg.sofia.uni.fmi.mjt.lab3.udemy.exception;

public class CourseNotPurchasedException extends Exception {
    public CourseNotPurchasedException(String message) {
        super(message);
    }

    public CourseNotPurchasedException(String message, Throwable cause) {
        super(message, cause);
    }
}
