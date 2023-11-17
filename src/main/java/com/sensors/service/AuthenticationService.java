package com.sensors.service;

import com.sensors.dao.RoleRepository;
import com.sensors.dto.request.AuthenticationRequest;
import com.sensors.dto.request.RegisterRequest;
import com.sensors.dto.response.AuthenticationResponse;
import com.sensors.model.entity.Role;
import com.sensors.model.entity.User;
import com.sensors.dao.TokenRepository;
import com.sensors.dao.UserRepository;
import com.sensors.model.entity.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        List<Role> roles = roleRepository.findAllById(request.getRoleIds());

        validateExistingUser(request);

        var user = User.builder()
                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .enabled(true)
                .build();

        var savedUser = userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken.getToken());

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    private void validateExistingUser(RegisterRequest request) {
        Optional<User> user = userRepository.findByLogin(request.getLogin());
        if (user.isPresent()) {
            throw new RuntimeException("Current user exists: " + request.getLogin());
        }

    }

}