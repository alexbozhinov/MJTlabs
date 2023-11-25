package bg.sofia.uni.fmi.mjt.lab3.udemy.exception;

import javax.naming.AuthenticationException;

public class CourseNotFoundException extends Exception {
    public CourseNotFoundException(String message) {
        super(message);
    }

    public CourseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
