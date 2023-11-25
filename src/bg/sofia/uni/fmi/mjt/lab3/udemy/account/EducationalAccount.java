package bg.sofia.uni.fmi.mjt.lab3.udemy.account;

import bg.sofia.uni.fmi.mjt.lab3.udemy.account.type.AccountType;
import bg.sofia.uni.fmi.mjt.lab3.udemy.course.Course;
import bg.sofia.uni.fmi.mjt.lab3.udemy.exception.CourseAlreadyPurchasedException;
import bg.sofia.uni.fmi.mjt.lab3.udemy.exception.InsufficientBalanceException;
import bg.sofia.uni.fmi.mjt.lab3.udemy.exception.MaxCourseCapacityReachedException;

public class EducationalAccount extends AccountBase {
    public EducationalAccount(String username, double balance) {
        super(username, balance);
        type = AccountType.EDUCATION;
    }

    @Override
    public void buyCourse(Course course)
        throws InsufficientBalanceException, CourseAlreadyPurchasedException, MaxCourseCapacityReachedException {
        if (null == course) {
            throw new IllegalArgumentException("Course to buy was null");
        }

        double discountedPrice = 0.0;

        if (getCourses().length >= 5) {
            double avgGrade = 0.0;
            for (int i = getCourses().length - 1; i >= getCourses().length - 5; i--) {
                if (getCourses()[i].isCompleted()) {
                    avgGrade += getCourses()[i].getGrade();
                } else {
                    avgGrade = 0.0;
                    break;
                }
            }

            if (avgGrade / 5.0 >= 4.50) {
                discountedPrice = (1 - type.getDiscount()) * course.getPrice();
            }
        }

        if (discountedPrice > getBalance()) {
            throw new InsufficientBalanceException("Not enough money in balance to buy course");
        }

        for (int i = 0; i < getCourses().length; i++) {
            if (getCourses()[i].equals(course)) {
                throw new CourseAlreadyPurchasedException("Given course was already purchased");
            }
        }

        if (getCourses().length >= 100) {
            throw new MaxCourseCapacityReachedException("Course purchased limit reached");
        }

        Course[] newArray = new Course[getCourses().length + 1];
        System.arraycopy(getCourses(), 0, newArray, 0, getCourses().length);
        newArray[getCourses().length] = course;
        setCourses(newArray);
        getCourses()[getCourses().length - 1].purchase();
        if (discountedPrice > 0.0) {
            setBalance(getBalance() - discountedPrice);
        } else {
            setBalance(getBalance() - course.getPrice());
        }
    }
}
