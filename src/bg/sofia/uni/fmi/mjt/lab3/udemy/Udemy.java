package bg.sofia.uni.fmi.mjt.lab3.udemy;

import bg.sofia.uni.fmi.mjt.lab3.udemy.account.Account;
import bg.sofia.uni.fmi.mjt.lab3.udemy.course.Category;
import bg.sofia.uni.fmi.mjt.lab3.udemy.course.Course;
import bg.sofia.uni.fmi.mjt.lab3.udemy.exception.AccountNotFoundException;
import bg.sofia.uni.fmi.mjt.lab3.udemy.exception.CourseNotFoundException;

public class Udemy implements LearningPlatform {
    private Account[] accounts;
    private Course[] courses;

    public Udemy(Account[] accounts, Course[] courses) {
        setAccounts(accounts);
        setCourses(courses);
    }

    @Override
    public Course findByName(String name) throws CourseNotFoundException {
        if (null == name) {
            throw new IllegalArgumentException("Name parameter is null");
        }
        for (Course course : courses) {
            if (course.getName().equals(name)) {
                return course;
            }
        }

        throw new CourseNotFoundException("Course not found");
    }

    private static boolean onlyAlphabets(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        for (int i = 0; i < str.length(); i++) {
            if (!Character.isLetter(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Course[] findByKeyword(String keyword) {
        Course[] containedCourses = new Course[0];

        if (null == keyword) {
            throw new IllegalArgumentException("keyword is null");
        }
        if (keyword.isBlank()) {
            throw new IllegalArgumentException("keyword is blank");
        }

        for (Course course : courses) {
            if ((course.getName().contains(keyword) && onlyAlphabets(keyword))
                || (course.getDescription().contains(keyword)) && onlyAlphabets(keyword)) {
                Course[] newArray = new Course[containedCourses.length + 1];
                System.arraycopy(containedCourses, 0, newArray, 0, containedCourses.length);
                newArray[containedCourses.length] = course;
                containedCourses = newArray;
            }
        }

        return containedCourses;
    }

    @Override
    public Course[] getAllCoursesByCategory(Category category) {
        Course[] categoryCourses = new Course[0];

        if (null == category) {
            throw new IllegalArgumentException("Category is null");
        }

        for (Course course : courses) {
            if (course.getCategory().equals(category)) {
                Course[] newArray = new Course[categoryCourses.length + 1];
                System.arraycopy(categoryCourses, 0, newArray, 0, categoryCourses.length);
                newArray[categoryCourses.length] = course;
                categoryCourses = newArray;
            }
        }

        return categoryCourses;
    }

    @Override
    public Account getAccount(String name) throws AccountNotFoundException {
        if (null == name) {
            throw new IllegalArgumentException("Name is null");
        }

        for (Account account : accounts) {
            if (account.getUsername().equals(name)) {
                return account;
            }
        }

        throw new AccountNotFoundException("Account with given username not found");
    }

    @Override
    public Course getLongestCourse() {
        if (courses == null || courses[0] == null) {
            return null;
        }

        Course longest = courses[0];
        for (int i = 1; i < courses.length; i++) {
            if ((courses[i].getTotalTime().minutes() + courses[i].getTotalTime().hours() * 60) >
                longest.getTotalTime().minutes() + longest.getTotalTime().hours() * 60) {
                longest = courses[i];
            }
        }

        return longest;
    }

    @Override
    public Course getCheapestByCategory(Category category) {
        if (null == category) {
            throw new IllegalArgumentException("Category is null");
        }
        if (null == courses || null == courses[0]) {
            return null;
        }

        Course cheapest = courses[0];
        int idx = 0;

        for (Course course : courses) {
            if (course.getCategory().equals(category)) {
                cheapest = course;
                idx++;
                break;
            }
            idx++;
        }

        for (int i = idx; i < courses.length; i++) {
            if (cheapest.getCategory().equals(category) &&
                courses[i].getCategory().equals(category) &&
                courses[i].getPrice() < cheapest.getPrice()
            ) {
                cheapest = courses[i];
            }
        }

        return cheapest;
    }

    public Account[] getAccounts() {
        return accounts;
    }

    public void setAccounts(Account[] accounts) {
        if (null == accounts) {
            this.accounts = new Account[0];
        } else {
            this.accounts = accounts;
        }
    }

    public Course[] getCourses() {
        return courses;
    }

    public void setCourses(Course[] courses) {
        if (null == courses) {
            this.courses = new Course[0];
        } else {
            this.courses = courses;
        }
    }
}
