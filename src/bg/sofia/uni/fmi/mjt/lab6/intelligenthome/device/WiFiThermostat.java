package bg.sofia.uni.fmi.mjt.lab6.intelligenthome.device;

import java.time.LocalDateTime;

public class WiFiThermostat extends IoTDeviceBase {
    private final String thermostatID;
    private final DeviceType type;

    public WiFiThermostat(String name, double powerConsumption, LocalDateTime installationDateTime) {
        super(name, powerConsumption, installationDateTime);

        type = DeviceType.THERMOSTAT;
        thermostatID = type.getShortName() + '-' + name + '-' + uniqueNumberDevice;
        uniqueNumberDevice++;
    }

    @Override
    public DeviceType getType() {
        return type;
    }

    @Override
    public String getId() {
        return thermostatID;
    }

}