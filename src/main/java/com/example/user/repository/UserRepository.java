package com.example.user.repository;

import com.example.dto.UserDTO;

import java.util.List;

public interface UserRepository {
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO);
    UserDTO getUserById(String id);
    List<UserDTO> getAllUsers();
    void deleteUser(String id);
}
