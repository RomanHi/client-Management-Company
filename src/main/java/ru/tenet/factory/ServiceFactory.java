package ru.tenet.factory;

import ru.tenet.model.BaseSensorInfo;
import ru.tenet.model.SensorStation;
import ru.tenet.model.SensorType;

public class ServiceFactory {

    public static BaseSensorInfo getService(SensorStation station) {
        SensorType sensorType = station.getSensorType();
        return null;
    }
}
