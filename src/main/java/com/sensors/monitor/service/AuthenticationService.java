package com.sensors.monitor.service;

import com.sensors.monitor.dao.RoleRepository;
import com.sensors.monitor.dao.TokenRepository;
import com.sensors.monitor.dao.UserRepository;
import com.sensors.monitor.model.dto.request.AuthenticationRequest;
import com.sensors.monitor.model.dto.request.RegisterRequest;
import com.sensors.monitor.model.dto.response.AuthenticationResponse;
import com.sensors.monitor.model.entity.Token;
import com.sensors.monitor.model.entity.user.Role;
import com.sensors.monitor.model.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws BadCredentialsException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByLogin(request.getLogin())
                .orElseThrow();
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken.getToken());
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokensByUserId(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        List<Role> userRoles = request.getRoles().stream()
                .map(role -> Role.builder()
                        .role(role)
                        .build())
                .collect(Collectors.toList());

        List<Role> savedRoles = roleRepository.saveAll(userRoles);


        var user = User.builder()
                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(savedRoles)
                .enabled(true)
                .build();

        var savedUser = userRepository.save(user);





        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken.getToken());

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

}