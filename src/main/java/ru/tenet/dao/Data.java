package ru.tenet.dao;


import ru.tenet.model.BaseSensorInfo;
import ru.tenet.model.SensorStation;

/**
 * base dao interface for get data from sensor
 *
 */
public interface Data {
    Object getDataByAddress(SensorStation station);

    BaseSensorInfo getSensorInfo(SensorStation station);
}
