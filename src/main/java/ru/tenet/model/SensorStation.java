package ru.tenet.model;

import lombok.Builder;
import lombok.Data;

/**
 * main model, which contain sensor station info
 */

@Data
@Builder
public class SensorStation {
    private SensorType sensorType;
    private String ipAddress;
    private int port;
    private String realAddress;
}
