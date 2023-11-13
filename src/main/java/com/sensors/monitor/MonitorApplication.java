package com.sensors.monitor;

import com.sensors.monitor.dto.request.RegisterRequest;
import com.sensors.monitor.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class MonitorApplication {

    private final AuthenticationService authenticationService;

    public static void main(String[] args) {
        SpringApplication.run(MonitorApplication.class, args);
    }


    //       Use it for the very first run to generate admin user
    @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service
    ) {
        return args -> {
            var admin = RegisterRequest.builder()
                    .login("admin")
                    .password("password")
                    .roleIds(List.of(1))
                    .build();
            System.out.println("Admin token: " + authenticationService.register(admin).getAccessToken().getToken().toString());


            var user = RegisterRequest.builder()
                    .login("user")
                    .password("password")
                    .roleIds(List.of(2))
                    .build();
            System.out.println("User token: " + authenticationService.register(user).getAccessToken().getToken().toString());
        };
    }

}
