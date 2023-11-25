package bg.sofia.uni.fmi.mjt.lab3.udemy.account;

import bg.sofia.uni.fmi.mjt.lab3.udemy.account.type.AccountType;
import bg.sofia.uni.fmi.mjt.lab3.udemy.course.Course;
import bg.sofia.uni.fmi.mjt.lab3.udemy.course.Resource;
import bg.sofia.uni.fmi.mjt.lab3.udemy.exception.*;

import java.util.Arrays;
import java.util.Objects;

public class AccountBase implements Account {
    private String username;
    private double balance;
    protected AccountType type;
    private Course[] courses = new Course[0];

    public AccountBase(String username, double balance) {
        this.username = username;
        this.balance = balance;
    }

    public void setUsername(String username) {
        this.username = Objects.requireNonNullElse(username, "");
    }

    public void setBalance(double balance) {
        this.balance = Math.max(balance, 0.0);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void addToBalance(double amount) {
        if (amount < 0.0) {
            throw new IllegalArgumentException("Amount less than 0.0");
        }

        balance += amount;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void buyCourse(Course course)
        throws InsufficientBalanceException, CourseAlreadyPurchasedException, MaxCourseCapacityReachedException {
        if (null == course) {
            throw new IllegalArgumentException("Course to buy was null");
        }

        double discountedPrice = (1 - type.getDiscount()) * course.getPrice();

        if (discountedPrice > balance) {
            throw new InsufficientBalanceException("Not enough money in balance to buy course");
        }

        for (int i = 0; i < courses.length; i++) {
            if (courses[i].equals(course)) {
                throw new CourseAlreadyPurchasedException("Given course was already purchased");
            }
        }

        if (courses.length >= 100) {
            throw new MaxCourseCapacityReachedException("Course purchased limit reached");
        }

        Course[] newArray = new Course[courses.length + 1];
        System.arraycopy(courses, 0, newArray, 0, courses.length);
        newArray[courses.length] = course;
        courses = newArray;
        courses[courses.length - 1].purchase();
        balance -= discountedPrice;
    }

    @Override
    public void completeResourcesFromCourse(Course course, Resource[] resourcesToComplete)
        throws CourseNotPurchasedException, ResourceNotFoundException {
        if (null == course) {
            throw new IllegalArgumentException("Given course is null");
        }
        if (null == resourcesToComplete) {
            throw new IllegalArgumentException("Given resources is null");
        }
        if (!Arrays.asList(courses).contains(course)) {
            throw new CourseNotPurchasedException("Given course is not purchased");
        }
        for (Resource resource : resourcesToComplete) {
            if (!Arrays.asList(course.getContent()).contains(resource)) {
                throw new ResourceNotFoundException("Some of resources not part of the course");
            }
        }

        for (int i = 0; i < course.getContent().length; i++) {
            for (Resource resource : resourcesToComplete) {
                if (resource.equals(course.getContent()[i])) {
                    course.getContent()[i].complete();
                }
            }
        }
    }

    @Override
    public void completeCourse(Course course, double grade)
        throws CourseNotPurchasedException, CourseNotCompletedException {
        if (null == course) {
            throw new IllegalArgumentException("Course is null");
        }
        if (grade < 2.00 || grade > 6.00) {
            throw new IllegalArgumentException("Grade not in appropriate interval");
        }
        if (!Arrays.asList(courses).contains(course)) {
            throw new CourseNotPurchasedException("Given course is not purchased");
        }
        if (!course.isCompleted()) {
            throw new CourseNotCompletedException("Not all resources of the course are completed");
        }

        course.setCompleted(true);
        course.setGrade(grade);
    }

    @Override
    public Course getLeastCompletedCourse() {
        Course least;
        if (null == courses[0]) {
            return null;
        } else {
            least = courses[0];
            for (int i = 1; i < courses.length; i++) {
                if (courses[i] != null) {
                    if (courses[i].getCompletionPercentage() < least.getCompletionPercentage()) {
                        least = courses[i];
                    }
                }
            }
        }

        return least;
    }

    public Course[] getCourses() {
        return courses;
    }

    public void setCourses(Course[] courses) {
        this.courses = courses;
    }
}
