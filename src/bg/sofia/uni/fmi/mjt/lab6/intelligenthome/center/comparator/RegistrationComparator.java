package bg.sofia.uni.fmi.mjt.lab6.intelligenthome.center.comparator;

import bg.sofia.uni.fmi.mjt.lab6.intelligenthome.device.IoTDevice;

import java.util.Comparator;

public class RegistrationComparator implements Comparator<IoTDevice> {

    @Override
    public int compare(IoTDevice firstDevice, IoTDevice secondDevice) {
        return (int) ((firstDevice.getRegistration() - secondDevice.getRegistration()) * (-1));
    }
}