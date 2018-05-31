package ru.tenet.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TV7SensorInfo extends BaseSensorInfo {
    private int type;
    private double softwareVersion;
    private double sensorVersion;
    private int softwareChecksum;
    private int model;
    private int reserve;
    private long serialNumber;
}
