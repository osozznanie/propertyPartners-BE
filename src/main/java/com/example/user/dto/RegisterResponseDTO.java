package com.example.user.dto;

import com.example.user.enums.RegisterStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponseDTO {
    private RegisterStatus status;
    private String message;
    private UserDTO userDTO;
}
