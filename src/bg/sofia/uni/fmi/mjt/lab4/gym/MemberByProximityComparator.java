package bg.sofia.uni.fmi.mjt.lab4.gym;

import bg.sofia.uni.fmi.mjt.lab4.gym.member.Address;
import bg.sofia.uni.fmi.mjt.lab4.gym.member.GymMember;

import java.util.Comparator;

public class MemberByProximityComparator implements Comparator<GymMember> {
    private final Address gymAddress;

    public MemberByProximityComparator(Address gymAddress) {
        this.gymAddress = gymAddress;
    }

    @Override
    public int compare(GymMember member1, GymMember member2) {
        double distance1 = member1.getAddress().getDistanceTo(gymAddress);
        double distance2 = member2.getAddress().getDistanceTo(gymAddress);
        return Double.compare(distance1, distance2);
    }
}
