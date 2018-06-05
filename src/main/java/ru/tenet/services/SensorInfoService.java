package ru.tenet.services;

import io.netty.buffer.ByteBufUtil;
import ru.tenet.model.BaseSensorInfo;
import ru.tenet.model.SensorType;
import ru.tenet.model.TV7SensorInfo;
import ru.tenet.model.VKT7SensorInfo;
import ru.tenet.utility.Converter;

public class SensorInfoService {

    private SensorType sensorType;

    public SensorInfoService(SensorType sensorType) {
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

    //TODO Write correct parser scheme measure according doc pg 17
    private BaseSensorInfo parseVKT7(short[] res) {
        int index = 0;

        double softwareVersion;
        int schemaMeasuring1;
        int schemaMeasuring2;
        int subscriberId;
        int networkNumber;
        int date;
        int model;

        softwareVersion = Converter.mergeOneByteToDouble(res[index]);
        index++;
        schemaMeasuring1 = res[index] + res[index + 1];
        index += 2;
        schemaMeasuring2 = res[index] + res[index + 1];
        index += 2;
        subscriberId = res[index];
        index++;
        networkNumber = res[index];
        index++;
        date = res[index];
        index++;
        model = res[index];

        return VKT7SensorInfo.builder()
                .date(date)
                .model(model)
                .networkNumber(networkNumber)
                .schemaMeasuring1(schemaMeasuring1)
                .schemaMeasuring2(schemaMeasuring2)
                .softwareVersion(softwareVersion)
                .subscriberId(subscriberId).build();
    }

    private BaseSensorInfo parseTV7(short[] res) throws IllegalStateException {

        int index = 0;

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
        index += 2;

        model = res[index];
        index++;

        reserve = res[index];
        index++;

        String hexDump = ByteBufUtil.hexDump(new byte[]{(byte) res[index], (byte) res[index + 1], (byte) res[index + 2], (byte) res[index + 3]});
        serialNumber = Long.decode("0x"+hexDump);

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
