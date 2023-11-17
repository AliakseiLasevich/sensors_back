package com.sensors.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {

    @JsonProperty("access_token")
    private TokenResponse accessToken;
}