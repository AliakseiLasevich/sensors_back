package com.sensors.monitor.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sensors.monitor.model.dto.TokenDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {

    @JsonProperty("access_token")
    private TokenDto accessToken;
}