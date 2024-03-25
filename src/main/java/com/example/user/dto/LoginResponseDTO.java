package com.example.user.dto;

import com.example.user.enums.LoginStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDTO {
    private LoginStatus status;
    private String message;
    private UserDTO userDTO;
    private String jwtToken;
}
