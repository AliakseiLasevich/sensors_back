package com.sensors.monitor.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenDto {

    @JsonProperty("token")
    private String token;
    @JsonProperty("expires_in")
    private long expiresIn;
}
