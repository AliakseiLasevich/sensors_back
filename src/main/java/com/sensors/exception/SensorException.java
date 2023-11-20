package com.sensors.exception;

public class SensorException extends RuntimeException {
    public SensorException() {
        super();
    }

    public SensorException(String message) {
        super(message);
    }
}