package com.sensors.monitor.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SensorRequest {
    private String name;
    private String model;
    private int rangeFrom;
    private int rangeTo;
    private String type;
    private String unit;
    private String location;
    private String description;
}