package com.sensors.monitor.model.entity;

import com.sensors.monitor.model.enums.SensorType;
import com.sensors.monitor.model.enums.SensorUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "sensors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sensor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @Size(max = 30, message = "Name must be at most 30 characters long.")
    private String name;

    @Column(name = "model")
    @Size(max = 15, message = "Model must be at most 15 characters long.")
    private String model;

    @Column(name = "rangeFrom")
    private int rangeFrom;

    @Column(name = "rangeTo")
    private int rangeTo;

    @Enumerated(EnumType.STRING)
    private SensorType type;

    @Enumerated(EnumType.STRING)
    private SensorUnit unit;

    @Column(name = "location")
    @Size(max = 40, message = "Location must be at most 15 characters long.")
    private String location;

    @Column(name = "description")
    @Size(max = 200, message = "Description must be at most 15 characters long.")
    private String description;

}