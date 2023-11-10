package com.sensors.monitor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/sensor")
@RequiredArgsConstructor
public class SensorController {

    @GetMapping("/test")
    @PreAuthorize("hasRole('ADMIN')")
    public String testAdmin() {
        return "hello admin";
    }

    @GetMapping("/test-user")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String authenticate() {
        return "hello user";
    }

}
