package bg.sofia.uni.fmi.mjt.lab3.udemy.course.duration;

public record ResourceDuration(int minutes) {
    public ResourceDuration {
        if (minutes < 0 || minutes > 60) {
            throw new IllegalArgumentException("More minutes than expected");
        }
    }
}
