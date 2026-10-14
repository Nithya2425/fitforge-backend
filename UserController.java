package com.fitforge.controller;

import com.fitforge.dto.response.UserResponse;
import com.fitforge.model.User;
import com.fitforge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getProfile() {
        return ResponseEntity.ok(userService.getProfile());
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateProfile(@RequestBody User updates) {
        return ResponseEntity.ok(userService.updateProfile(updates));
    }

    @GetMapping("/admin/members")
    public ResponseEntity<List<UserResponse>> getAllMembers() {
        return ResponseEntity.ok(userService.getAllMembers());
    }

    @GetMapping("/admin/trainers")
    public ResponseEntity<List<UserResponse>> getAllTrainers() {
        return ResponseEntity.ok(userService.getAllTrainers());
    }

    @GetMapping("/admin/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PatchMapping("/admin/users/{id}/toggle")
    public ResponseEntity<UserResponse> toggleStatus(@PathVariable Long id) {
        return ResponseEntity.ok(userService.toggleUserStatus(id));
    }
}
