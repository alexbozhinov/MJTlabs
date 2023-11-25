package bg.sofia.uni.fmi.mjt.lab3.udemy.account;
import bg.sofia.uni.fmi.mjt.lab3.udemy.account.type.AccountType;
import bg.sofia.uni.fmi.mjt.lab3.udemy.course.Category;
import bg.sofia.uni.fmi.mjt.lab3.udemy.course.Course;
import bg.sofia.uni.fmi.mjt.lab3.udemy.exception.CourseAlreadyPurchasedException;
import bg.sofia.uni.fmi.mjt.lab3.udemy.exception.InsufficientBalanceException;
import bg.sofia.uni.fmi.mjt.lab3.udemy.exception.MaxCourseCapacityReachedException;

import java.util.Arrays;

public class BusinessAccount extends AccountBase{
    private Category[] allowedCategories;

    public BusinessAccount(String username, double balance, Category[] allowedCategories) {
        super(username, balance);
        type = AccountType.BUSINESS;
        setAllowedCategories(allowedCategories);
    }

    @Override
    public void buyCourse(Course course) throws InsufficientBalanceException, CourseAlreadyPurchasedException, MaxCourseCapacityReachedException {
        if (null == course){
            throw new IllegalArgumentException("Course to buy was null");
        }

        double discountedPrice = (1 - type.getDiscount()) * course.getPrice();

        if (discountedPrice > getBalance()){
            throw new InsufficientBalanceException("Not enough money in balance to buy course");
        }

        for (int i = 0; i < getCourses().length; i++) {
            if (getCourses()[i].equals(course)){
                throw new CourseAlreadyPurchasedException("Given course was already purchased");
            }
        }

        if (getCourses().length >= 100){
            throw new MaxCourseCapacityReachedException("Course purchased limit reached");
        }

        if (!Arrays.asList(allowedCategories).contains(course.getCategory())){
            throw new IllegalArgumentException("Course with not allowed category");
        }

        Course[] newArray = new Course[getCourses().length + 1];
        System.arraycopy(getCourses(), 0, newArray, 0, getCourses().length);
        newArray[getCourses().length] = course;
        setCourses(newArray);
        getCourses()[getCourses().length - 1].purchase();
        setBalance(getBalance() - discountedPrice);
    }

    public Category[] getAllowedCategories() {
        return allowedCategories;
    }

    public void setAllowedCategories(Category[] allowedCategories) {
        this.allowedCategories = allowedCategories;
    }
}
