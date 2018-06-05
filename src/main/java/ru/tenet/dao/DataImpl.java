package ru.tenet.dao;


import ru.tenet.model.BaseSensorInfo;
import ru.tenet.model.SensorStation;
import ru.tenet.services.SensorInfoService;

/**
 * some impl for base dao
 */
public class DataImpl implements Data {

    @Override
    public Object getDataByAddress(SensorStation station) {
        return null;
    }

    @Override
    public BaseSensorInfo getSensorInfo(SensorStation station) {
        SensorInfoService service = new SensorInfoService(station.getSensorType());
        service.getRequest();
        return null;
    }
}
