package com.example.user.mapper;

import com.example.user.domain.User;
import com.example.user.dto.UserDTO;
import com.example.user.dto.UserRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .isApproved(user.isApproved())
                .build();
    }
    public User toEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .isApproved(userDTO.isApproved())
                .build();
    }

    public UserRequestDTO toRequest(User user) {
        return UserRequestDTO.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public User toEntity(UserRequestDTO userRequestDTO) {
        return User.builder()
                .email(userRequestDTO.getEmail())
                .password(userRequestDTO.getPassword())
                .build();
    }

}
