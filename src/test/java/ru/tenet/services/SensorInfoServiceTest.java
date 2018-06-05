package ru.tenet.services;

import org.junit.Test;
import ru.tenet.model.BaseSensorInfo;
import ru.tenet.model.SensorType;
import ru.tenet.model.TV7SensorInfo;
import ru.tenet.model.VKT7SensorInfo;

import static org.junit.Assert.*;

public class SensorInfoServiceTest {
    private SensorInfoService service;


    @Test
    public void getRequest() {
        SensorInfoService vkt7 = new SensorInfoService(SensorType.VKT7);
        SensorInfoService tv7 = new SensorInfoService(SensorType.TV7);
        SensorInfoService mk5 = new SensorInfoService(SensorType.KM5);

        byte[] expectedVKT7 = new byte[]{0x00, 0x01, 0x00, 0x00, 0x00, 0x06, 0x01, 0x03, 0x3f, (byte) 0xf9, 0x00, 0x00};
        byte[] expectedTV7 = new byte[]{0x00, 0x01, 0x00, 0x00, 0x00, 0x06, 0x01, 0x03, 0x00, 0x00, 0x00, 0x05};
        byte[] expectedMK5 = null;//new byte[]{};

        assertArrayEquals(expectedVKT7, vkt7.getRequest());
        assertArrayEquals(expectedTV7, tv7.getRequest());
        assertArrayEquals(expectedMK5, mk5.getRequest());

        byte number = 125;
        for (int i = 7; i >= 0; i--) {
            if (((1 << i) & number) != 0) {
                System.out.print(1);
            } else {
                System.out.print(0);
            }

        }

    }

    @Test
    public void parseResponseForVKT7ExpectedNewCorrectVKTSensorInfo() {
        short[] goodResponse = new short[]{0x18, 0x25, 0x25, 0x25, 0x25, 'c', 0x0a, 0x0e, 0x30};

        service = new SensorInfoService(SensorType.VKT7);
        VKT7SensorInfo expected = VKT7SensorInfo.builder()
                .softwareVersion(1.8)
                .schemaMeasuring1(37 + 37)
                .schemaMeasuring2(37 + 37)
                .subscriberId('c')
                .networkNumber(0x0a)
                .date(0x0e)
                .model(0x30).build();

        VKT7SensorInfo result = (VKT7SensorInfo) service.parseResponse(goodResponse);
        assertEquals(expected, result);
        System.out.println(result);
    }

    @Test
    public void parseResponseForTV7ExpectedNewCorrectTV7SensorInfo() {
        short[] goodResponse = new short[]{0x17, 0x02, 0x02, 0x08, 0x01, 0x08, 0x81, 0x08, 0x03, 0x05, 0xFF, 0xFF, 0xFF, 0xFF};
        service = new SensorInfoService(SensorType.TV7);
        TV7SensorInfo expected = TV7SensorInfo.builder()
                .type(5890)
                .softwareVersion(2.8)
                .sensorVersion(1.8)
                .softwareChecksum(33032)
                .model(3)
                .reserve(5)
                .serialNumber(4294967295L).build();


        BaseSensorInfo result = service.parseResponse(goodResponse);
        System.out.println(result);
        assertEquals(expected, result);
    }
}