package ru.tenet.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VKT7SensorInfo extends BaseSensorInfo {
    private double softwareVersion;
    private int schemaMeasuring1;
    private int schemaMeasuring2;
    private int subscriberId;
    private int networkNumber;
    private int date;
    private int model;
}
