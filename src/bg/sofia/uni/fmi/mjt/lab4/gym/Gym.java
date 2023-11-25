package bg.sofia.uni.fmi.mjt.lab4.gym;

import bg.sofia.uni.fmi.mjt.lab4.gym.member.Address;
import bg.sofia.uni.fmi.mjt.lab4.gym.member.GymMember;
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
import java.util.SortedSet;
import java.util.TreeSet;

public class Gym implements GymAPI {
    private int capacity;
    private Address address;
    SortedSet<GymMember> members;

    public Gym(int capacity, Address address) {
        setCapacity(capacity);
        setAddress(address);
        this.members = new TreeSet<>();
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = Math.max(capacity, 0);
    }

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

    public void setMembers(SortedSet<GymMember> members) {
        if (null == members) {
            this.members = new TreeSet<>();
        } else {
            this.members = members;
        }
    }

    @Override
    public SortedSet<GymMember> getMembers() {
        return Collections.unmodifiableSortedSet(members);
    }

    @Override
    public SortedSet<GymMember> getMembersSortedByName() {
        return Collections.unmodifiableSortedSet(new TreeSet<>(members));
    }

    @Override
    public SortedSet<GymMember> getMembersSortedByProximityToGym() {
        List<GymMember> membersList = new ArrayList<>(members);
        membersList.sort(new MemberByProximityComparator(address));

        return Collections.unmodifiableSortedSet(new TreeSet<>(membersList));
    }

    @Override
    public void addMember(GymMember member) throws GymCapacityExceededException {
        if (null == member) {
            throw new IllegalArgumentException("Given member is null");
        }
        if (capacity == members.size()) {
            throw new GymCapacityExceededException("Maximum gym capacity reached");
        }

        members.add(member);
    }

    @Override
    public void addMembers(Collection<GymMember> members) throws GymCapacityExceededException {
        if (null == members) {
            throw new IllegalArgumentException("Given members is null");
        }
        if (members.isEmpty()) {
            throw new IllegalArgumentException("Given members is empty");
        }
        if (members.size() + members.size() > capacity) {
            throw new GymCapacityExceededException("Maximum gym capacity reached");
        }

        this.members.addAll(members);
    }

    @Override
    public boolean isMember(GymMember member) {
        if (null == member) {
            throw new IllegalArgumentException("Given member is null");
        }

        return members.contains(member);
    }

    @Override
    public boolean isExerciseTrainedOnDay(String exerciseName, DayOfWeek day) {
        boolean isTrained = false;

        if (null == exerciseName) {
            throw new IllegalArgumentException("Given exerciseName is null");
        }
        if (null == day) {
            throw new IllegalArgumentException("Given day is null");
        }
        if (exerciseName.isEmpty()) {
            throw new IllegalArgumentException("Given exerciseName is empty");
        }

        for (GymMember member : members) {
            for (Exercise exercise : member.getTrainingProgram().get(day).exercises()) {
                if (exercise.name().equals(exerciseName)) {
                    isTrained = true;
                    break;
                }
            }
        }

        return isTrained;
    }

    @Override
    public Map<DayOfWeek, List<String>> getDailyListOfMembersForExercise(String exerciseName) {
        if (null == exerciseName) {
            throw new IllegalArgumentException("Given exerciseName is null");
        }
        if (exerciseName.isEmpty()) {
            throw new IllegalArgumentException("Given exerciseName is empty");
        }

        Map<DayOfWeek, List<String>> dailyListOfMembers = new HashMap<>();

        for (GymMember member : members) {
            for (Map.Entry<DayOfWeek, Workout> entry : member.getTrainingProgram().entrySet()) {
                for (Exercise exercise : entry.getValue().exercises()) {
                    if (exercise.name().equals(exerciseName)) {
                        if (!dailyListOfMembers.containsKey(entry.getKey())) {
                            dailyListOfMembers.put(entry.getKey(), new ArrayList<>());
                        }
                        dailyListOfMembers.get(entry.getKey()).add(member.getName());
                        break;
                    }
                }
            }
        }

        return Collections.unmodifiableMap(dailyListOfMembers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gym gym = (Gym) o;
        return capacity == gym.capacity && Objects.equals(address, gym.address) &&
            Objects.equals(members, gym.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(capacity, address, members);
    }
}
