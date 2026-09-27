package com.example.apisecurity.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.apisecurity.dto.AuthResponse;
import com.example.apisecurity.dto.LoginRequest;
import com.example.apisecurity.dto.RegisterRequest;
import com.example.apisecurity.entity.Role;
import com.example.apisecurity.entity.User;
import com.example.apisecurity.repository.UserRepository;
import com.example.apisecurity.security.JwtService;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already registered");
        }

        User user = new User(
                request.username(),
                passwordEncoder.encode(request.password()),
                Role.DEVELOPER
        );
        User savedUser = userRepository.save(user);
        return createResponse(savedUser);
    }

    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
        } catch (BadCredentialsException exception) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
        return createResponse(user);
    }

    private AuthResponse createResponse(User user) {
        return new AuthResponse(
                jwtService.generateToken(user),
                user.getUsername(),
                user.getRole(),
                user.getCreatedAt()
        );
    }
}
