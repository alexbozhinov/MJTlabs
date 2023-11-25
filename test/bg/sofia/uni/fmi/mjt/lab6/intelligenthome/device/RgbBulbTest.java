package bg.sofia.uni.fmi.mjt.lab6.intelligenthome.device;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RgbBulbTest {
    @Test
    void testDeviceIDCorrectID() {
        String expectedID = "BLB-name-" + IoTDeviceBase.uniqueNumberDevice;
        RgbBulb device = new RgbBulb("name", 100.0, LocalDateTime.now());
        assertEquals(expectedID , device.getId());
    }
}
