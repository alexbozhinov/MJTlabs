package bg.sofia.uni.fmi.mjt.lab3.udemy.course.duration;

import bg.sofia.uni.fmi.mjt.lab3.udemy.course.Resource;

public record CourseDuration(int hours, int minutes) {
    public static CourseDuration of(Resource[] content) {
        int allMinutes = 0;

        for (Resource resource : content) {
            allMinutes += resource.getDuration().minutes();
        }

        return new CourseDuration(allMinutes / 60, allMinutes % 60);
    }

    public CourseDuration {
        if (hours < 0 || hours > 24) {
            throw new IllegalArgumentException("More hours than expected");
        }

        if (minutes < 0 || minutes > 60) {
            throw new IllegalArgumentException("More minutes than expected");
        }
    }
}
