package bg.sofia.uni.fmi.mjt.lab4.gym.workout;

import java.util.Objects;

public record Exercise(String name, int sets, int repetitions) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return name.equals(exercise.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
