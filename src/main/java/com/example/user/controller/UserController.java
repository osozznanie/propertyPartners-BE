package com.example.user.controller;

import com.example.user.dto.LoginResponseDTO;
import com.example.user.dto.RegisterResponseDTO;
import com.example.user.dto.UserDTO;
import com.example.user.dto.UserRequestDTO;
import com.example.user.mapper.UserMapper;
import com.example.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Users", description = "API for managing users")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok(userService.register(userRequestDTO));
    }

    @Operation(summary = "Approve a user registration")
    @PostMapping("/approve/{id}")
    public ResponseEntity<RegisterResponseDTO> approveRegistration(@PathVariable String id) {
        return ResponseEntity.ok(userService.approveRegistration(id));
    }

    @Operation(summary = "Login a user")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok(userService.login(userMapper.toEntity(userRequestDTO)));
    }

    @Operation(summary = "Get a user by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}