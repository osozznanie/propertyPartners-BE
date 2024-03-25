package com.example.user.service;

import com.example.user.domain.User;
import com.example.user.dto.LoginResponseDTO;
import com.example.user.dto.RegisterResponseDTO;
import com.example.user.dto.UserDTO;
import com.example.user.dto.UserRequestDTO;
import com.example.user.enums.LoginStatus;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserRequestDTO userRequestDTO);

    UserDTO updateUser(UserRequestDTO userRequestDTO, String id);

    UserDTO getUserById(String id);

    List<UserDTO> getAllUsers();

    void deleteUserById(String id);

    RegisterResponseDTO register(UserRequestDTO userRequestDTO);

    LoginResponseDTO login(User entity);

    UserDTO findByEmail(String email);

    RegisterResponseDTO approveRegistration(String id);
}
