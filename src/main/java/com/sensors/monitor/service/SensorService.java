package com.sensors.monitor.service;

import com.sensors.monitor.dao.SensorRepository;
import com.sensors.monitor.dto.request.SensorRequest;
import com.sensors.monitor.exception.SensorException;
import com.sensors.monitor.model.entity.Sensor;
import com.sensors.monitor.model.enums.SensorType;
import com.sensors.monitor.model.enums.SensorUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SensorService {
    private final SensorRepository sensorRepository;

    public List<Sensor> getSensors() {
        return sensorRepository.findAll();
    }

    public Sensor createSensor(SensorRequest request) {
        Sensor sensor = buildSensorFromRequest(request).build();
        return sensorRepository.save(sensor);
    }

    public Sensor updateSensor(Integer id, SensorRequest request) {
        findSensorById(id);
        Sensor sensorToUpdate = buildSensorFromRequest(request, id).build();
        return sensorRepository.save(sensorToUpdate);
    }

    private Sensor.SensorBuilder buildSensorFromRequest(SensorRequest request) {
        return Sensor.builder()
                .name(request.getName())
                .model(request.getModel())
                .type(SensorType.valueOf(request.getType()))
                .unit(SensorUnit.valueOf(request.getUnit()))
                .rangeFrom(request.getRangeFrom())
                .rangeTo(request.getRangeTo())
                .location(request.getLocation())
                .description(request.getDescription());
    }

    private Sensor.SensorBuilder buildSensorFromRequest(SensorRequest request, Integer id) {
        return buildSensorFromRequest(request).id(id);
    }

    public void deleteSensor(Integer id) {
        Optional<Sensor> sensor = findSensorById(id);
        sensorRepository.delete(sensor.get());
    }

    private Optional<Sensor> findSensorById(Integer id) {
        Optional<Sensor> sensor = sensorRepository.findById(id);
        if (sensor.isEmpty()) {
            log.error("No sensor found. id: {}", id);
            throw new SensorException("No sensor found. id: " + id);
        }
        return sensor;
    }
}
