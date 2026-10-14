package com.fitforge.config;

import com.fitforge.model.User;
import com.fitforge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public ApplicationRunner initData() {
        return args -> {
            if (!userRepository.existsByEmail("admin@fitforge.com")) {
                userRepository.save(User.builder()
                        .email("admin@fitforge.com")
                        .password(passwordEncoder.encode("admin123"))
                        .firstName("Admin")
                        .lastName("FitForge")
                        .role(User.Role.ADMIN)
                        .active(true)
                        .streakDays(0)
                        .totalPoints(0)
                        .build());
                log.info("Admin user created: admin@fitforge.com / admin123");
            }

            if (!userRepository.existsByEmail("trainer@fitforge.com")) {
                userRepository.save(User.builder()
                        .email("trainer@fitforge.com")
                        .password(passwordEncoder.encode("trainer123"))
                        .firstName("Riya")
                        .lastName("Sharma")
                        .role(User.Role.TRAINER)
                        .active(true)
                        .streakDays(15)
                        .totalPoints(500)
                        .build());
                log.info("Trainer user created: trainer@fitforge.com / trainer123");
            }

            if (!userRepository.existsByEmail("member@fitforge.com")) {
                userRepository.save(User.builder()
                        .email("member@fitforge.com")
                        .password(passwordEncoder.encode("member123"))
                        .firstName("Arjun")
                        .lastName("Kumar")
                        .role(User.Role.MEMBER)
                        .active(true)
                        .streakDays(7)
                        .totalPoints(200)
                        .build());
                log.info("Member user created: member@fitforge.com / member123");
            }
        };
    }
}
