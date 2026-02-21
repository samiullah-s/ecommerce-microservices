package com.example.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.auth.dto.AuthRequest;
import com.example.auth.dto.RegistrationRequest;
import com.example.auth.service.AuthService;
import com.example.auth.service.UserServiceImpl;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "User registration and login endpoints")
public class AuthController {

        private final AuthService authService;
        private final UserServiceImpl userService;

        public AuthController(AuthService authService, UserServiceImpl userService) {
                this.authService = authService;
                this.userService = userService;
        }

        @PostMapping("/register")
        @Operation(summary = "Register a new user")
        public ResponseEntity<String> register(@Valid @RequestBody RegistrationRequest request) {
                userService.register(request);
                return ResponseEntity.ok("User registered successfully");
        }

        @PostMapping("/login")
        @Operation(summary = "Login and get Jwt token")
        public ResponseEntity<String> login(@Valid @RequestBody AuthRequest request) {
                String token = authService.login(request.getUsername(), request.getPassword());
                return ResponseEntity.ok(token);
        }
}
