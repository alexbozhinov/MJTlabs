package bg.sofia.uni.fmi.mjt.lab4.gym.workout;

import java.util.Objects;
import java.util.SequencedCollection;

public record Workout(SequencedCollection<Exercise> exercises) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workout workout = (Workout) o;
        return Objects.equals(exercises, workout.exercises);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exercises);
    }
}
