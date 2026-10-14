package com.fitforge.repository;

import com.fitforge.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByRole(User.Role role);
    List<User> findByRoleAndActive(User.Role role, Boolean active);

    @Query("SELECT u FROM User u WHERE u.role = 'MEMBER' AND u.updatedAt < :cutoff AND u.active = true")
    List<User> findInactiveMembers(LocalDateTime cutoff);

    long countByRole(User.Role role);
    long countByRoleAndActive(User.Role role, Boolean active);
}
