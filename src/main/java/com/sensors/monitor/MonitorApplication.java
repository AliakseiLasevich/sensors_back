package com.sensors.monitor;

import com.sensors.monitor.model.dto.request.RegisterRequest;
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
//    @Bean
//    public CommandLineRunner commandLineRunner(
//            AuthenticationService service
//    ) {
//        return args -> {
//            var admin = RegisterRequest.builder()
//                    .login("admin")
//                    .password("password")
//                    .roles(List.of("ADMIN"))
//                    .build();
//            System.out.println("Admin token: " + authenticationService.register(admin).getAccessToken());
//        };
//    }

        @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service
    ) {
        return args -> {
            var admin = RegisterRequest.builder()
                    .login("user")
                    .password("password")
                    .roles(List.of("USER"))
                    .build();
            System.out.println("Admin token: " + authenticationService.register(admin).getAccessToken().getToken().toString());
        };
    }

}
