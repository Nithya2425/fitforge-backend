package com.fitforge.controller;

import com.fitforge.dto.response.DashboardStats;
import com.fitforge.model.User;
import com.fitforge.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardStats> getDashboard() {
        return ResponseEntity.ok(adminService.getDashboardStats());
    }

    @GetMapping("/churn-risk")
    public ResponseEntity<List<User>> getChurnRisk() {
        return ResponseEntity.ok(adminService.getChurnRiskMembers());
    }
}
