package bg.sofia.uni.fmi.mjt.lab6.intelligenthome.center;

import bg.sofia.uni.fmi.mjt.lab6.intelligenthome.device.DeviceType;
import bg.sofia.uni.fmi.mjt.lab6.intelligenthome.device.IoTDevice;
import bg.sofia.uni.fmi.mjt.lab6.intelligenthome.center.comparator.KWhComparator;
import bg.sofia.uni.fmi.mjt.lab6.intelligenthome.center.comparator.RegistrationComparator;
import bg.sofia.uni.fmi.mjt.lab6.intelligenthome.center.exceptions.DeviceAlreadyRegisteredException;
import bg.sofia.uni.fmi.mjt.lab6.intelligenthome.center.exceptions.DeviceNotFoundException;
import bg.sofia.uni.fmi.mjt.lab6.intelligenthome.storage.DeviceStorage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class IntelligentHomeCenter {
    DeviceStorage storage;

    public IntelligentHomeCenter(DeviceStorage storage) {
        this.storage = storage;
    }

    /**
     * Adds a @device to the IntelligentHomeCenter.
     *
     * @throws IllegalArgumentException         in case @device is null.
     * @throws DeviceAlreadyRegisteredException in case the @device is already
     *                                          registered.
     */
    public void register(IoTDevice device) throws DeviceAlreadyRegisteredException {
        if (device == null) {
            throw new IllegalArgumentException("device cannot be null");
        }
        if (storage.exists(device.getId())) {
            throw new DeviceAlreadyRegisteredException("Device is already registered");
        }

        storage.store(device.getId(), device);
        device.setRegistration(LocalDateTime.now());
    }

    /**
     * Removes the @device from the IntelligentHomeCenter.
     *
     * @throws IllegalArgumentException in case null is passed.
     * @throws DeviceNotFoundException  in case the @device is not found.
     */
    public void unregister(IoTDevice device) throws DeviceNotFoundException {
        if (device == null) {
            throw new IllegalArgumentException("device cannot be null");
        }
        if (!storage.exists(device.getId())) {
            throw new DeviceNotFoundException("Device not found");
        }

        storage.delete(device.getId());
    }

    /**
     * Returns a IoTDevice with an ID @id if found.
     *
     * @throws IllegalArgumentException in case @id is null or blank.
     * @throws DeviceNotFoundException  in case device with ID @id is not found.
     */
    public IoTDevice getDeviceById(String id) throws DeviceNotFoundException {
        if (id == null) {
            throw new IllegalArgumentException("device cannot be null");
        }
        if (id.isBlank()) {
            throw new IllegalArgumentException("device cannot be blank");
        }
        if (!storage.exists(id)) {
            throw new DeviceNotFoundException("device not found");
        }

        return storage.get(id);
    }

    /**
     * Returns the total number of devices with type @type registered in
     * SmartCityHub.
     *
     * @throws IllegalArgumentException in case @type is null.
     */
    public int getDeviceQuantityPerType(DeviceType type) {
        if (null == type) {
            throw new IllegalArgumentException("type is null");
        }

        int quantity = 0;

        for (IoTDevice value : storage.listAll()) {
            if (value.getType().getShortName().equals(type.getShortName())) {
                quantity++;
            }
        }

        return quantity;
    }

    public Collection<String> getTopNDevicesByPowerConsumption(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        List<IoTDevice> list = new LinkedList<>(storage.listAll());
        KWhComparator compareKWh = new KWhComparator();
        list.sort(compareKWh);
        if (n >= list.size()) {
            n = list.size();
        }
        List<String> arrList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            arrList.add(list.get(i).getId());
        }
        return arrList;
    }

    public Collection<IoTDevice> getFirstNDevicesByRegistration(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        List<IoTDevice> list = new LinkedList<>(storage.listAll());

        RegistrationComparator compareReg = new RegistrationComparator();
        list.sort(compareReg);

        List<IoTDevice> arrList = new ArrayList<>();

        if (n >= list.size()) {
            n = list.size();
        }

        for (int i = 0; i < n; i++) {
            arrList.add(list.get(i));
        }

        return arrList;
    }
}