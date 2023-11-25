package bg.sofia.uni.fmi.mjt.lab3.udemy.course;

import bg.sofia.uni.fmi.mjt.lab3.udemy.course.duration.CourseDuration;
import bg.sofia.uni.fmi.mjt.lab3.udemy.exception.ResourceNotFoundException;

import java.util.Arrays;
import java.util.Objects;

public class Course implements Completable, Purchasable {
    private String name;
    private String description;
    private double price;
    private Resource[] content;
    private Category category;
    private boolean purchased = false;
    private boolean completed = false;

    private double grade = 0.0;

    public Course(String name, String description, double price, Resource[] content, Category category) {
        setName(name);
        setDescription(description);
        setPrice(price);
        setContent(content);
        setCategory(category);
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNullElse(name, "");
    }

    public void setDescription(String description) {
        this.description = Objects.requireNonNullElse(description, "");
    }

    public void setPrice(double price) {
        this.price = Math.max(price, 0.0);
    }

    public void setContent(Resource[] content) {
        if (content == null) {
            this.content = new Resource[0];
        } else {
            this.content = content;
        }
    }

    public void setCategory(Category category) {
        this.category = Objects.requireNonNullElse(category, Category.DEVELOPMENT);
    }

    /**
     * Returns the name of the course.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the course.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the price of the course.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the category of the course.
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Returns the content of the course.
     */
    public Resource[] getContent() {
        return content;
    }

    /**
     * Returns the total duration of the course.
     */
    public CourseDuration getTotalTime() {
        return CourseDuration.of(content);
    }

    /**
     * Completes a resource from the course.
     *
     * @param resourceToComplete the resource which will be completed.
     * @throws IllegalArgumentException  if resourceToComplete is null.
     * @throws ResourceNotFoundException if the resource could not be found in the course.
     */
    public void completeResource(Resource resourceToComplete) throws ResourceNotFoundException {
        if (null == resourceToComplete) {
            throw new IllegalArgumentException("The resource is null");
        }
        if (!Arrays.asList(content).contains(resourceToComplete)) {
            throw new ResourceNotFoundException("Resource not found in contend");
        }

        for (Resource resource : content) {
            if (resourceToComplete.equals(resource)) {
                resource.complete();
            }
        }
    }

    @Override
    public boolean isCompleted() {
        int completed = 0;

        for (Resource resource : content) {
            if (resource.isCompleted()) {
                completed++;
            }
        }

        return completed == content.length;
    }

    @Override
    public int getCompletionPercentage() {
        int totalMinutes = getTotalTime().minutes() + getTotalTime().hours() * 60;
        int totalResources = 0;
        int completed = 0;

        for (Resource resource : content) {
            totalResources++;
            if (resource.isCompleted()) {
                completed++;
            }
        }

        if (totalResources > 0) {
            return (int) (Math.round(((double) completed / totalResources) * 100));

        } else {
            return 0;
        }
    }

    @Override
    public void purchase() {
        purchased = true;
    }

    @Override
    public boolean isPurchased() {
        return purchased;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
