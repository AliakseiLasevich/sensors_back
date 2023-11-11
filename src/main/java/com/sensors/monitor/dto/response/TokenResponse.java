package com.sensors.monitor.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponse {

    @JsonProperty("token")
    private String token;
    @JsonProperty("expires_in")
    private long expiresIn;
}
