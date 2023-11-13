package com.sensors.monitor.controller;

import com.sensors.monitor.dto.request.SensorRequest;
import com.sensors.monitor.model.entity.Sensor;
import com.sensors.monitor.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/sensors")
@RequiredArgsConstructor
public class SensorController {

    private final SensorService sensorService;

    @GetMapping
    @PreAuthorize("hasAnyRole('Viewer', 'Administrator')")
    @ResponseStatus(HttpStatus.OK)
    public List<Sensor> getSensors() {
        return sensorService.getSensors();
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('Administrator')")
    @ResponseStatus(HttpStatus.CREATED)
    public Sensor createSensor(@RequestBody SensorRequest sensorRequest) {
        return sensorService.createSensor(sensorRequest);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('Administrator')")
    @ResponseStatus(HttpStatus.OK)
    public Sensor updateSensor(@PathVariable Integer id, @RequestBody SensorRequest sensorRequest) {
        return sensorService.updateSensor(id, sensorRequest);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('Administrator')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSensor(@PathVariable Integer id) {
        sensorService.deleteSensor(id);
    }

}
