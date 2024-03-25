package com.example.user.controller;

import com.example.user.dto.LoginResponseDTO;
import com.example.user.dto.RegisterResponseDTO;
import com.example.user.dto.UserDTO;
import com.example.user.dto.UserRequestDTO;
import com.example.user.mapper.UserMapper;
import com.example.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok(userService.register(userRequestDTO));
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<RegisterResponseDTO> approveRegistration(@PathVariable String id) {
        return ResponseEntity.ok(userService.approveRegistration(id));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok(userService.login(userMapper.toEntity(userRequestDTO)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

}
