package ru.tenet.services;

import ru.tenet.model.BaseSensorInfo;
import ru.tenet.model.SensorType;
import ru.tenet.model.TV7SensorInfo;
import ru.tenet.utility.Converter;

public class SensorService {
    //TV7
    private final int TV7_INDEX_LONG_RESPONSE = 5;
    private final int TV7_START_DATA_INDEX = 9;
    private final int VKT7_START_DATA_INDEX = 9;

    private SensorType sensorType;

    public SensorService(SensorType sensorType) {
        this.sensorType = sensorType;
    }

    public byte[] getRequest() {

        switch (sensorType) {
            case KM5: {
                return getKM5Request();
            }
            case TV7: {
                return getTV7Request();
            }
            case VKT7: {
                return getVKT7Request();
            }
        }
        return null;
    }

    public BaseSensorInfo parseResponse(short[] res) throws IllegalStateException {
        switch (sensorType) {
            case KM5: {
                return parseKM5(res);
            }
            case TV7: {
                return parseTV7(res);
            }
            case VKT7: {
                return parseVKT7(res);
            }
        }
        return null;
    }

    //TODO Correct parse scheme measure according doc pg 17
    private BaseSensorInfo parseVKT7(short[] res) {
        int index =VKT7_START_DATA_INDEX;

        double softwareVersion;
        int schemaMeasuring1;
        int schemaMeasuring2;
        int subscriberId;
        int networkNumber;
        int date;
        int model;

        softwareVersion = Converter.mergeOneByteToDouble(res[index]);
        return null;
    }

    private BaseSensorInfo parseTV7(short[] res) {
        if (res[TV7_INDEX_LONG_RESPONSE] != 13) {
            throw new IllegalStateException("unexpected response");
        }
        int index = TV7_START_DATA_INDEX;

        int type;
        double softwareVersion;
        double sensorVersion;
        int softwareChecksum;
        int model;
        int reserve;
        long serialNumber;

        type = Converter.mergeTwoByteToInt(res[index], res[index + 1]);
        index += 2;
        softwareVersion = Converter.mergeTwoByteToDouble(res[index], res[index + 1]);
        index += 2;
        sensorVersion = Converter.mergeTwoByteToDouble(res[index], res[index + 1]);
        index += 2;
        softwareChecksum = Converter.mergeTwoByteToInt(res[index], res[index + 1]);
        index++;
        model = res[index];
        index++;
        reserve = res[index];

        //TODO Parse 4 index in long?
        serialNumber = res[index] + res[index + 1] + res[index + 2] + res[index + 3];

        return TV7SensorInfo.builder()
                .type(type)
                .model(model)
                .reserve(reserve)
                .sensorVersion(sensorVersion)
                .serialNumber(serialNumber)
                .softwareVersion(softwareVersion)
                .softwareChecksum(softwareChecksum).build();
    }

    private BaseSensorInfo parseKM5(short[] res) {
        return null;
    }

    private byte[] getVKT7Request() {
        byte[] request = new byte[]{0x00, 0x01, 0x00, 0x00, 0x00, 0x06, 0x01, 0x03, 0x3f, (byte) 0xf9, 0x00, 0x00};
        return request;
    }

    private byte[] getTV7Request() {
        byte[] request = new byte[]{0x00, 0x01, 0x00, 0x00, 0x00, 0x06, 0x01, 0x03, 0x00, 0x00, 0x00, 0x05};
        return request;
    }

    private byte[] getKM5Request() {
        byte[] request = new byte[]{0x00, 0x01, 0x00, 0x00, 0x00, 0x06};
        return null;//request;
    }
}
