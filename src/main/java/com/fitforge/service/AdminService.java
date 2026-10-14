package com.fitforge.service;

import com.fitforge.dto.response.DashboardStats;
import com.fitforge.model.User;
import com.fitforge.repository.AttendanceRepository;
import com.fitforge.repository.GymClassRepository;
import com.fitforge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;
    private final GymClassRepository classRepository;
    private final MembershipService membershipService;

    public DashboardStats getDashboardStats() {
        long totalMembers = userRepository.countByRole(User.Role.MEMBER);
        long activeMembers = userRepository.countByRoleAndActive(User.Role.MEMBER, true);
        long totalTrainers = userRepository.countByRole(User.Role.TRAINER);
        long todayAttendance = attendanceRepository.countByDateRange(LocalDate.now(), LocalDate.now());

        // Churn risk: members inactive for 14+ days
        LocalDateTime cutoff = LocalDateTime.now().minusDays(14);
        long churnRisk = userRepository.findInactiveMembers(cutoff).size();

        return DashboardStats.builder()
                .totalMembers(totalMembers)
                .activeMembers(activeMembers)
                .totalTrainers(totalTrainers)
                .todayAttendance(todayAttendance)
                .monthlyRevenue(membershipService.getMonthlyRevenue())
                .churnRiskCount(churnRisk)
                .build();
    }

    public List<User> getChurnRiskMembers() {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(14);
        return userRepository.findInactiveMembers(cutoff);
    }

    public User createUser(User user, String rawPassword) {
        return userRepository.save(user);
    }
}
