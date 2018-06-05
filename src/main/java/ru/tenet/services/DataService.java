package ru.tenet.services;

import ru.tenet.model.SensorType;

public class DataService {
    SensorType sensorType;

    public DataService(SensorType sensorType) {
        this.sensorType = sensorType;
    }

    public byte[] getRequest() {
        return null;
    }

    private byte[] vkt7Request() {
        byte[] request = new byte[]{};
        return request;
    }

    private byte[] tv7Request() {
        byte[] request = new byte[]{};
        return request;
    }

    private void parseProperties(short[] res) {

    }
}
