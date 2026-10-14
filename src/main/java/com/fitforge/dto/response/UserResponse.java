package com.fitforge.dto.response;

import com.fitforge.model.User;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String role;
    private Boolean active;
    private Integer streakDays;
    private Integer totalPoints;
    private LocalDateTime createdAt;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .role(user.getRole().name())
                .active(user.getActive())
                .streakDays(user.getStreakDays())
                .totalPoints(user.getTotalPoints())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
