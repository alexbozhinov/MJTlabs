package bg.sofia.uni.fmi.mjt.lab5.simcity.plot;

import bg.sofia.uni.fmi.mjt.lab5.simcity.exceptions.BuildableAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.lab5.simcity.exceptions.BuildableNotFoundException;
import bg.sofia.uni.fmi.mjt.lab5.simcity.exceptions.InsufficientPlotAreaException;
import bg.sofia.uni.fmi.mjt.lab5.simcity.property.buildable.Buildable;

import java.util.HashMap;
import java.util.Map;

public class Plot<E extends Buildable> implements PlotAPI<E> {

    private final int totalBuildableArea;
    private int remainingBuildableArea;
    private final Map<String, E> buildables;

    public Plot(int buildableArea) {
        this.totalBuildableArea = buildableArea;
        this.remainingBuildableArea = buildableArea;
        this.buildables = new HashMap<>();
    }

    @Override
    public void construct(String address, E buildable) {
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Address cannot be null or blank");
        }
        if (buildable == null) {
            throw new IllegalArgumentException("Buildable cannot be null");
        }
        if (buildables.containsKey(address)) {
            throw new BuildableAlreadyExistsException("Buildable already exists at the specified address");
        }
        if (buildable.getArea() > remainingBuildableArea) {
            throw new InsufficientPlotAreaException("Insufficient plot area for construction");
        }

        buildables.put(address, buildable);
        remainingBuildableArea -= buildable.getArea();
    }

    @Override
    public void constructAll(Map<String, E> buildables) {
        if (buildables == null || buildables.isEmpty()) {
            throw new IllegalArgumentException("Map of buildables cannot be null or empty");
        }

        for (Map.Entry<String, E> entry : buildables.entrySet()) {
            construct(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void demolish(String address) {
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Address cannot be null or blank");
        }
        if (!buildables.containsKey(address)) {
            throw new BuildableNotFoundException("Buildable not found at the specified address");
        }

        E demolishedBuildable = buildables.remove(address);
        remainingBuildableArea += demolishedBuildable.getArea();
    }

    @Override
    public void demolishAll() {
        buildables.clear();
        remainingBuildableArea = totalBuildableArea;
    }

    @Override
    public Map<String, E> getAllBuildables() {
        return Map.copyOf(buildables);
    }

    @Override
    public int getRemainingBuildableArea() {
        return remainingBuildableArea;
    }
}
