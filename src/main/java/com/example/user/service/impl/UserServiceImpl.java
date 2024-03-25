package com.example.user.service.impl;

import com.example.exception.EntityNotFoundException;
import com.example.security.JwtTokenUtil;
import com.example.user.domain.PendingRegistration;
import com.example.user.domain.User;
import com.example.user.dto.LoginResponseDTO;
import com.example.user.dto.RegisterResponseDTO;
import com.example.user.dto.UserDTO;
import com.example.user.dto.UserRequestDTO;
import com.example.user.enums.LoginStatus;
import com.example.user.enums.RegisterStatus;
import com.example.user.mapper.UserMapper;
import com.example.user.repository.PendingRegistrationRepository;
import com.example.user.repository.UserRepository;
import com.example.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PendingRegistrationRepository pendingRegistrationRepository;
    private final UserMapper userMapper;
    private final JwtTokenUtil jwtTokenUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDTO createUser(UserRequestDTO userRequestDTO) {
        User user = userMapper.toEntity(userRequestDTO);
        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    public UserDTO updateUser(UserRequestDTO userRequestDTO, String id) {
        User user = userMapper.toEntity(userRequestDTO);
        user.setId(id);
        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    public UserDTO getUserById(String id) {
        return userMapper.toDTO(userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found by id: " + id)
        ));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public RegisterResponseDTO register(UserRequestDTO userRequestDTO) {
        if (userRepository.findByEmail(userRequestDTO.getEmail()) != null) {
            return RegisterResponseDTO.builder()
                    .status(RegisterStatus.FAILED)
                    .message("User already exists")
                    .build();
        }

        User user = userMapper.toEntity(userRequestDTO);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setApproved(false);
        PendingRegistration pendingRegistration = new PendingRegistration(user);
        user.setId(pendingRegistrationRepository.save(pendingRegistration).getId());


        return RegisterResponseDTO.builder()
                .status(RegisterStatus.PENDING)
                .message("Registration pending approval")
                .userDTO(userMapper.toDTO(user))
                .build();
    }

    @Override
    public RegisterResponseDTO approveRegistration(String id) {
        PendingRegistration pendingRegistration = pendingRegistrationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pending registration not found"));

        User user = pendingRegistration.getUser();
        user.setApproved(true);

        User savedUser = userRepository.save(user);
        pendingRegistrationRepository.deleteById(id);

        return RegisterResponseDTO.builder()
                .status(RegisterStatus.SUCCESS)
                .message("User approved successfully")
                .userDTO(userMapper.toDTO(savedUser))
                .build();
    }

    @Override
    public LoginResponseDTO login(User entity) {
        User user = userRepository.findByEmail(entity.getEmail());

        if (invalidUser(entity, user)) {
            return LoginResponseDTO.builder()
                    .status(LoginStatus.FAILED)
                    .message("Invalid user")
                    .build();
        }

        String token = jwtTokenUtil.generateToken(user);

        return LoginResponseDTO.builder()
                .status(LoginStatus.SUCCESS)
                .message("User logged in successfully")
                .jwtToken(token)
                .userDTO(userMapper.toDTO(user))
                .build();
    }

    @Override
    public UserDTO findByEmail(String email) {
        return userMapper.toDTO(userRepository.findByEmail(email));
    }

    private boolean invalidUser(User userFromRequest, User userFromDb) {
        return userFromRequest == null ||
                userFromDb == null ||
                !userFromRequest.getEmail().equals(userFromDb.getEmail()) ||
                !bCryptPasswordEncoder.matches(userFromRequest.getPassword(), userFromDb.getPassword());
    }
}
