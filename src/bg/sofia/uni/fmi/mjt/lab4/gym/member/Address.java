package bg.sofia.uni.fmi.mjt.lab4.gym.member;

import java.util.Objects;

public record Address(double longitude, double latitude) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Double.compare(longitude, address.longitude) == 0 &&
            Double.compare(latitude, address.latitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(longitude, latitude);
    }

    public double getDistanceTo(Address other) {
        return Math.sqrt(
            Math.pow(this.longitude - other.longitude(), 2) +
                Math.pow(this.latitude - other.latitude(), 2)
        );
    }
}
