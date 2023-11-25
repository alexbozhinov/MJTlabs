package bg.sofia.uni.fmi.mjt.lab3.udemy.course;

import bg.sofia.uni.fmi.mjt.lab3.udemy.course.duration.ResourceDuration;

import java.util.Objects;

public class Resource implements Completable {
    private String name;
    private ResourceDuration duration;

    private boolean completed = false;

    public Resource(String name, ResourceDuration duration) {
        setName(name);
        setDuration(duration);
    }

    public void setName(String name) {
        this.name = Objects.requireNonNullElse(name, "");
    }

    public void setDuration(ResourceDuration duration) {
        this.duration = duration;
    }

    /**
     * Returns the resource name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the total duration of the resource.
     */
    public ResourceDuration getDuration() {
        return duration;
    }

    /**
     * Marks the resource as completed.
     */
    public void complete() {
        completed = true;
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public int getCompletionPercentage() {
        return (int) ((duration.minutes() / 60.0) * 100);
    }
}
