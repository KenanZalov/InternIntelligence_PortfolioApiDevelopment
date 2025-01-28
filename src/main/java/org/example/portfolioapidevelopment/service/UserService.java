package org.example.portfolioapidevelopment.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.portfolioapidevelopment.dao.MyUser;
import org.example.portfolioapidevelopment.dto.request.UserRequestDto;
import org.example.portfolioapidevelopment.dto.response.UserResponseDto;
import org.example.portfolioapidevelopment.exception.DuplicateEntityException;
import org.example.portfolioapidevelopment.exception.UnauthorizedAccessException;
import org.example.portfolioapidevelopment.repository.MyUserRepository;
import org.example.portfolioapidevelopment.security.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final MyUserRepository myUserRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String addUser(UserRequestDto userRequestDto) {
        log.info("Starting addUser method for username: {}", userRequestDto.getUsername());

        if (myUserRepository.existsByUsername(userRequestDto.getUsername())) {
            log.warn("Username already exists: {}", userRequestDto.getUsername());
            throw new DuplicateEntityException("User", "username", userRequestDto.getUsername());
        }
        MyUser myUser = modelMapper.map(userRequestDto, MyUser.class);
        myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
        myUserRepository.save(myUser);

        log.info("User created successfully with username: {}", userRequestDto.getUsername());
        return "User created";
    }

    public List<UserResponseDto> getAllUsers() {
        log.info("Starting getAllUsers method");

        List<MyUser> myUsers = myUserRepository.findAll();
        log.info("Found {} users in the database", myUsers.size());

        return myUsers
                .stream()
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .toList();
    }

    public String updateUser(Long userId, UserRequestDto userRequestDto) {
        log.info("Starting updateUser method for userId: {}", userId);

        String currentUsername = jwtService.getCurrentUsername();
        if (!isUserOwner(userId, currentUsername)) {
            log.error("Access denied for user: {} while updating userId: {}", currentUsername, userId);

            throw new UnauthorizedAccessException("You are not authorized to update this user");
        }
        MyUser myUser = myUserRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User with id: {} not found", userId);
                    return new EntityNotFoundException("User not found!");
                });
        log.info("Updating user with id: {}", userId);
        myUser.setUsername(userRequestDto.getUsername());
        myUser.setPassword(userRequestDto.getPassword());
        myUser.setEmail(userRequestDto.getEmail());
        myUserRepository.save(myUser);

        log.info("User with id: {} updated successfully", userId);
        return "User updated";
    }

    public String deleteUser(Long userId) {
        log.info("Starting deleteUser method for userId: {}", userId);

        String currentUsername = jwtService.getCurrentUsername();
        if (!isUserOwner(userId, currentUsername)) {
            log.error("Access denied for user: {} while deleting userId: {}", currentUsername, userId);

            throw new UnauthorizedAccessException("You are not authorized to delete this user");
        }
        MyUser myUser = myUserRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User with id: {} not found ", userId);
                    return new EntityNotFoundException("User not found!");
                });
        log.info("Deleting user with id: {}", userId);
        myUserRepository.delete(myUser);

        log.info("User with id: {} deleted successfully", userId);
        return "User deleted";
    }

    public boolean isUserOwner(Long userId, String username) {
        MyUser user = myUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return user.getUsername().equals(username);
    }



}
