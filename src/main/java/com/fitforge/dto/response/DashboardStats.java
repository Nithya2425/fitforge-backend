package com.fitforge.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class DashboardStats {
    // Admin stats
    private Long totalMembers;
    private Long activeMembers;
    private Long totalTrainers;
    private Long activeClasses;
    private BigDecimal monthlyRevenue;
    private Long todayAttendance;
    private Long churnRiskCount;

    // Member stats
    private Integer streakDays;
    private Integer totalWorkouts;
    private Integer thisWeekWorkouts;
    private Integer totalPoints;
    private String membershipStatus;
    private String membershipExpiry;

    // Trainer stats
    private Long assignedMembers;
    private Long activePlans;
    private Long todayClasses;
}
