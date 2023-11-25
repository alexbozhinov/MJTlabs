package bg.sofia.uni.fmi.mjt.lab4.gym.member;

import bg.sofia.uni.fmi.mjt.lab4.gym.workout.Exercise;
import bg.sofia.uni.fmi.mjt.lab4.gym.workout.Workout;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Member implements GymMember, Comparable<Member> {
    private Address address;
    private String name;
    private int age;
    private String personalIdNumber;
    private Gender gender;
    private Map<DayOfWeek, Workout> trainingProgram = new HashMap<>();

    public Member(Address address, String name, int age, String personalIdNumber, Gender gender) {
        setAddress(address);
        setName(name);
        setAge(age);
        setPersonalIdNumber(personalIdNumber);
        setGender(gender);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        if (null == address) {
            this.address = new Address(0.0, 0.0);
        } else {
            this.address = address;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (null == name) {
            this.name = "";
        } else {
            this.name = name;
        }
    }

    @Override
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = Math.max(age, 0);
    }

    @Override
    public String getPersonalIdNumber() {
        return personalIdNumber;
    }

    public void setPersonalIdNumber(String personalIdNumber) {
        if (null == personalIdNumber) {
            this.personalIdNumber = "";
        } else {
            this.personalIdNumber = personalIdNumber;
        }
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        if (null == gender) {
            this.gender = Gender.MALE;
        } else {
            this.gender = gender;
        }
    }

    @Override
    public Map<DayOfWeek, Workout> getTrainingProgram() {
        return Collections.unmodifiableMap(trainingProgram);
    }

    public void setTrainingProgram(Map<DayOfWeek, Workout> trainingProgram) {
        if (null == trainingProgram) {
            this.trainingProgram = new HashMap<>();
        } else {
            this.trainingProgram = trainingProgram;
        }
    }

    @Override
    public void setWorkout(DayOfWeek day, Workout workout) {
        if (null == day) {
            throw new IllegalArgumentException("Given day of week is null");
        }
        if (null == workout) {
            throw new IllegalArgumentException("Given workout is null");
        }

        trainingProgram.put(day, workout);
    }

    @Override
    public Collection<DayOfWeek> getDaysFinishingWith(String exerciseName) {
        if (null == exerciseName) {
            throw new IllegalArgumentException("Given exerciseName is null");
        }
        if (exerciseName.isEmpty()) {
            throw new IllegalArgumentException("Given exerciseName is empty");
        }

        Collection<DayOfWeek> days = new ArrayList<>();
        for (Map.Entry<DayOfWeek, Workout> entry : trainingProgram.entrySet()) {
            if (entry.getValue().exercises().getLast().name().equals(exerciseName)) {
                days.add(entry.getKey());
            }
        }

        return days;
    }

    @Override
    public void addExercise(DayOfWeek day, Exercise exercise) {
        if (null == day) {
            throw new IllegalArgumentException("Given day is null");
        }
        if (null == exercise) {
            throw new IllegalArgumentException("Given exercise is null");
        }

        try {
            trainingProgram.get(day).exercises().add(exercise);
        } catch (NullPointerException e) {
            throw new DayOffException("workout on given day is null", e);
        }
    }

    @Override
    public void addExercises(DayOfWeek day, List<Exercise> exercises) {
        if (null == day) {
            throw new IllegalArgumentException("Given day is null");
        }
        if (null == exercises) {
            throw new IllegalArgumentException("Given exercises is null");
        }
        if (exercises.isEmpty()) {
            throw new IllegalArgumentException("Given exercises is empty");
        }

        try {
            trainingProgram.get(day).exercises().addAll(exercises);
        } catch (NullPointerException e) {
            throw new DayOffException("workout on given day is null", e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(personalIdNumber, member.personalIdNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personalIdNumber);
    }

    @Override
    public int compareTo(Member other) {
        return this.getName().compareTo(other.getName());
    }
}
