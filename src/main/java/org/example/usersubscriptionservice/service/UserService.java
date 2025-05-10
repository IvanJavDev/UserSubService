package org.example.usersubscriptionservice.service;

import org.example.usersubscriptionservice.dto.UserCreateDTO;
import org.example.usersubscriptionservice.dto.UserResponseDTO;
import org.example.usersubscriptionservice.entity.UserEntity;
import org.example.usersubscriptionservice.exceptions.UserNotFoundExeption;
import org.example.usersubscriptionservice.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserResponseDTO createUser(UserCreateDTO userCreateDTO) {
        logger.info("Creating new user with email: {}", userCreateDTO.getEmail());
        UserEntity user = new UserEntity();
        user.setName(userCreateDTO.getName());
        user.setEmail(userCreateDTO.getEmail());
        UserEntity savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserResponseDTO.class);
    }

    public UserResponseDTO getUserById(Long id) {
        logger.info("Fetching user with id: {}", id);
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundExeption("User not found"));
        return modelMapper.map(user, UserResponseDTO.class);
    }

    public UserResponseDTO updateUser(Long id, UserResponseDTO userResponseDTO) {
        logger.info("Updating user with id: {}", id);
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundExeption("User not found"));

        modelMapper.map(userResponseDTO, existingUser);
        UserEntity updatedUser = userRepository.save(existingUser);
        return modelMapper.map(updatedUser, UserResponseDTO.class);
    }

    public void deleteUser(Long id) {
        logger.info("Deleting user with id: {}", id);
        userRepository.deleteById(id);
    }

    public List<UserResponseDTO> getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserResponseDTO.class))
                .collect(Collectors.toList());
    }
}
