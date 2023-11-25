package bg.sofia.uni.fmi.mjt.lab6.intelligenthome.center;

import bg.sofia.uni.fmi.mjt.lab6.intelligenthome.center.exceptions.DeviceAlreadyRegisteredException;
import bg.sofia.uni.fmi.mjt.lab6.intelligenthome.center.exceptions.DeviceNotFoundException;
import bg.sofia.uni.fmi.mjt.lab6.intelligenthome.device.AmazonAlexa;
import bg.sofia.uni.fmi.mjt.lab6.intelligenthome.device.DeviceType;
import bg.sofia.uni.fmi.mjt.lab6.intelligenthome.device.IoTDevice;
import bg.sofia.uni.fmi.mjt.lab6.intelligenthome.device.IoTDeviceBase;
import bg.sofia.uni.fmi.mjt.lab6.intelligenthome.device.RgbBulb;
import bg.sofia.uni.fmi.mjt.lab6.intelligenthome.device.WiFiThermostat;
import bg.sofia.uni.fmi.mjt.lab6.intelligenthome.storage.MapDeviceStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collection;

public class IntelligentHomeCenterTest {

    @Test
    void testRegisterAlreadyRegisteredDevice() throws DeviceAlreadyRegisteredException {
        IntelligentHomeCenter homeCenter = new IntelligentHomeCenter(new MapDeviceStorage());
        AmazonAlexa device = new AmazonAlexa("name", 100.0, LocalDateTime.now());
        homeCenter.register(device);
        Assertions.assertThrows(DeviceAlreadyRegisteredException.class, () -> homeCenter.register(device), "Trying to register already registered device");
    }

    @Test
    void testRegisterNullDevice() {
        IntelligentHomeCenter homeCenter = new IntelligentHomeCenter(new MapDeviceStorage());
        Assertions.assertThrows(IllegalArgumentException.class, () -> homeCenter.register(null), "Trying to register null device");
    }

    @Test
    void testRegisterSuccessfullyRegisterDevice() throws DeviceAlreadyRegisteredException {
        IntelligentHomeCenter homeCenter = new IntelligentHomeCenter(new MapDeviceStorage());
        AmazonAlexa device = new AmazonAlexa("name", 100.0, LocalDateTime.now());
        homeCenter.register(device);

        Assertions.assertTrue(homeCenter.storage.exists(device.getId()));
    }

    @Test
    void testUnregisterNonExistingDevice() {
        IntelligentHomeCenter homeCenter = new IntelligentHomeCenter(new MapDeviceStorage());
        AmazonAlexa device = new AmazonAlexa("name", 100.0, LocalDateTime.now());
        Assertions.assertThrows(DeviceNotFoundException.class, () -> homeCenter.unregister(device), "Trying to unregister non existing device");
    }

    @Test
    void testUnregisterNullDevice() {
        IntelligentHomeCenter homeCenter = new IntelligentHomeCenter(new MapDeviceStorage());
        Assertions.assertThrows(IllegalArgumentException.class, () -> homeCenter.unregister(null), "Trying to unregister null device");
    }

    @Test
    void testUnregisterSuccessfullyUnregisterDevice() throws DeviceAlreadyRegisteredException, DeviceNotFoundException {
        IntelligentHomeCenter homeCenter = new IntelligentHomeCenter(new MapDeviceStorage());
        AmazonAlexa device = new AmazonAlexa("name", 100.0, LocalDateTime.now());
        homeCenter.register(device);
        homeCenter.unregister(device);

        Assertions.assertFalse(homeCenter.storage.exists(device.getId()));
    }

    @Test
    void testGetDeviceByIdNullID() {
        IntelligentHomeCenter homeCenter = new IntelligentHomeCenter(new MapDeviceStorage());
        Assertions.assertThrows(IllegalArgumentException.class, () -> homeCenter.getDeviceById(null));
    }

    @Test
    void testGetDeviceByIdBlankID() {
        IntelligentHomeCenter homeCenter = new IntelligentHomeCenter(new MapDeviceStorage());
        String id = "";
        Assertions.assertThrows(IllegalArgumentException.class, () -> homeCenter.getDeviceById(id));
    }

    @Test
    void testGetDeviceByIdNonExistingID() {
        IntelligentHomeCenter homeCenter = new IntelligentHomeCenter(new MapDeviceStorage());
        String id = "name-3";
        Assertions.assertThrows(DeviceNotFoundException.class, () -> homeCenter.getDeviceById(id));
    }

    @Test
    void testGetDeviceByIdCorrectDevice() throws DeviceAlreadyRegisteredException, DeviceNotFoundException {
        IntelligentHomeCenter homeCenter = new IntelligentHomeCenter(new MapDeviceStorage());
        AmazonAlexa device = new AmazonAlexa("name", 100.0, LocalDateTime.now());
        homeCenter.register(device);
        Assertions.assertEquals("name", homeCenter.getDeviceById(device.getId()).getName());
    }

    @Test
    void testGetDeviceQuantityPerTypeNullType() {
        IntelligentHomeCenter homeCenter = new IntelligentHomeCenter(new MapDeviceStorage());
        Assertions.assertThrows(IllegalArgumentException.class, () -> homeCenter.getDeviceQuantityPerType(null));
    }

    @Test
    void testGetDeviceQuantityPerTypeOneBlb() throws DeviceAlreadyRegisteredException {
        IntelligentHomeCenter homeCenter = new IntelligentHomeCenter(new MapDeviceStorage());
        IoTDeviceBase blb = new RgbBulb("blb", 100.0, LocalDateTime.now());
        IoTDeviceBase alexa = new AmazonAlexa("alexa", 200.0, LocalDateTime.now());
        IoTDeviceBase wifi = new WiFiThermostat("WiFi", 300.0, LocalDateTime.now());
        homeCenter.register(blb);
        homeCenter.register(alexa);
        homeCenter.register(wifi);
        Assertions.assertEquals(1, homeCenter.getDeviceQuantityPerType(DeviceType.BULB));
        Assertions.assertEquals(1, homeCenter.getDeviceQuantityPerType(wifi.getType()));
        Assertions.assertEquals(1, homeCenter.getDeviceQuantityPerType(alexa.getType()));
    }

    @Test
    void testGetTopNDevicesByPowerConsumptionNegativeN() {
        IntelligentHomeCenter homeCenter = new IntelligentHomeCenter(new MapDeviceStorage());
        Assertions.assertThrows(IllegalArgumentException.class, () -> homeCenter.getTopNDevicesByPowerConsumption(-1));
    }

    @Test
    void testGetTopNDevicesByPowerConsumptionTopOne() throws DeviceAlreadyRegisteredException {
        IntelligentHomeCenter homeCenter = new IntelligentHomeCenter(new MapDeviceStorage());
        IoTDeviceBase blb1 = new RgbBulb("blb", 100.0, LocalDateTime.now());
        IoTDeviceBase alexa1 = new AmazonAlexa("alexa", 200.0, LocalDateTime.now());
        homeCenter.register(blb1);
        homeCenter.register(alexa1);
        Collection<String> list = homeCenter.getTopNDevicesByPowerConsumption(1);
        Assertions.assertTrue(list.contains(alexa1.getId()));
    }

    @Test
    void testGetTopNDevicesByPowerConsumptionBiggerN() throws DeviceAlreadyRegisteredException {
        IntelligentHomeCenter homeCenter = new IntelligentHomeCenter(new MapDeviceStorage());
        IoTDeviceBase blb2 = new RgbBulb("blb", 100.0, LocalDateTime.now());
        IoTDeviceBase alexa2 = new AmazonAlexa("alexa", 200.0, LocalDateTime.now());
        homeCenter.register(blb2);
        homeCenter.register(alexa2);
        Collection<String> list = homeCenter.getTopNDevicesByPowerConsumption(10);
        Assertions.assertEquals(2, list.size());
    }

    @Test
    void testGetFirstNDevicesByRegistrationNegativeN() {
        IntelligentHomeCenter homeCenter = new IntelligentHomeCenter(new MapDeviceStorage());
        Assertions.assertThrows(IllegalArgumentException.class, () -> homeCenter.getFirstNDevicesByRegistration(-1));
    }

    @Test
    void testGetFirstNDevicesByRegistrationTopOne() throws DeviceAlreadyRegisteredException {
        IntelligentHomeCenter homeCenter = new IntelligentHomeCenter(new MapDeviceStorage());
        IoTDeviceBase blb3 = new RgbBulb("blb", 100.0, LocalDateTime.of(2012, 4, 5, 12, 34, 26));
        IoTDeviceBase alexa3 = new AmazonAlexa("alexa", 200.0, LocalDateTime.of(2023, 11, 25, 22, 25, 36));
        homeCenter.register(blb3);
        homeCenter.register(alexa3);
        Collection<IoTDevice> list = homeCenter.getFirstNDevicesByRegistration(1);
        Assertions.assertTrue(list.contains(alexa3));
        Assertions.assertEquals(alexa3.getPowerConsumption(), list.iterator().next().getPowerConsumption());
    }

    @Test
    void testGetFirstNDevicesByRegistrationBiggerN() throws DeviceAlreadyRegisteredException {
        IntelligentHomeCenter homeCenter = new IntelligentHomeCenter(new MapDeviceStorage());
        IoTDeviceBase blb4 = new RgbBulb("blb", 100.0, LocalDateTime.of(2012, 4, 5, 12, 34, 26));
        IoTDeviceBase alexa4 = new AmazonAlexa("alexa", 200.0, LocalDateTime.of(2023, 11, 25, 22, 25, 36));
        homeCenter.register(blb4);
        homeCenter.register(alexa4);
        Collection<IoTDevice> list = homeCenter.getFirstNDevicesByRegistration(3);
        Assertions.assertEquals(2, list.size());
    }

    @Test
    void testDeleteNonExistingDeviceById() {
        IntelligentHomeCenter homeCenter = new IntelligentHomeCenter(new MapDeviceStorage());
        Assertions.assertFalse(homeCenter.storage.delete("SPKR-alexa8-5"));
    }
}
