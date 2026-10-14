package com.fitforge.service;

import com.fitforge.dto.response.UserResponse;
import com.fitforge.exception.ResourceNotFoundException;
import com.fitforge.model.User;
import com.fitforge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public UserResponse getProfile() {
        return UserResponse.from(getCurrentUser());
    }

    public UserResponse updateProfile(User updates) {
        User user = getCurrentUser();
        if (updates.getFirstName() != null) user.setFirstName(updates.getFirstName());
        if (updates.getLastName() != null) user.setLastName(updates.getLastName());
        if (updates.getPhone() != null) user.setPhone(updates.getPhone());
        return UserResponse.from(userRepository.save(user));
    }

    public List<UserResponse> getAllMembers() {
        return userRepository.findByRole(User.Role.MEMBER)
                .stream().map(UserResponse::from).collect(Collectors.toList());
    }

    public List<UserResponse> getAllTrainers() {
        return userRepository.findByRole(User.Role.TRAINER)
                .stream().map(UserResponse::from).collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) {
        return UserResponse.from(userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found")));
    }

    public UserResponse toggleUserStatus(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setActive(!user.getActive());
        return UserResponse.from(userRepository.save(user));
    }

    public void addPoints(User user, int points) {
        user.setTotalPoints(user.getTotalPoints() + points);
        userRepository.save(user);
    }
}
