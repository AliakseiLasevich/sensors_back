package com.sensors.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SensorUnit {
    BAR("bar"), VOLTAGE("voltage"), CELSIUS("°С"), PERCENTAGE("%");

    private String unit;
}