package bg.sofia.uni.fmi.mjt.lab5.simcity.utility;

import bg.sofia.uni.fmi.mjt.lab5.simcity.property.billable.Billable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UtilityService implements UtilityServiceAPI {

    private final Map<UtilityType, Double> taxRates;

    public UtilityService(Map<UtilityType, Double> taxRates) {
        this.taxRates = Map.copyOf(taxRates);
    }

    @Override
    public <T extends Billable> double getUtilityCosts(UtilityType utilityType, T billable) {
        if (utilityType == null || billable == null) {
            throw new IllegalArgumentException("Utility type and billable cannot be null");
        }

        double taxRate = taxRates.getOrDefault(utilityType, 0.0);
        double monthlyConsumption = switch (utilityType) {
            case WATER -> billable.getWaterConsumption();
            case ELECTRICITY -> billable.getElectricityConsumption();
            case NATURAL_GAS -> billable.getNaturalGasConsumption();
            default -> throw new IllegalArgumentException("Unsupported utility type");
        };

        return taxRate * monthlyConsumption;
    }

    @Override
    public <T extends Billable> double getTotalUtilityCosts(T billable) {
        double totalCosts = 0.0;

        for (UtilityType utilityType : UtilityType.values()) {
            totalCosts += getUtilityCosts(utilityType, billable);
        }

        return totalCosts;
    }

    @Override
    public <T extends Billable> Map<UtilityType, Double> computeCostsDifference(T firstBillable, T secondBillable) {
        if (firstBillable == null || secondBillable == null) {
            throw new IllegalArgumentException("Billables cannot be null");
        }

        Map<UtilityType, Double> costsDifference = new HashMap<>();

        for (UtilityType utilityType : UtilityType.values()) {
            double firstCost = getUtilityCosts(utilityType, firstBillable);
            double secondCost = getUtilityCosts(utilityType, secondBillable);
            double difference = Math.abs(firstCost - secondCost);
            costsDifference.put(utilityType, difference);
        }

        return Collections.unmodifiableMap(costsDifference);
    }
}
