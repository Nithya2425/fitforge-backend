package com.fitforge.controller;

import com.fitforge.model.*;
import com.fitforge.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @PostMapping("/trainer/plans")
    public ResponseEntity<WorkoutPlan> createPlan(@RequestBody WorkoutPlan plan) {
        return ResponseEntity.ok(workoutService.createPlan(plan));
    }

    @GetMapping("/trainer/plans")
    public ResponseEntity<List<WorkoutPlan>> getMyPlans() {
        return ResponseEntity.ok(workoutService.getMyPlans());
    }

    @GetMapping("/plans/templates")
    public ResponseEntity<List<WorkoutPlan>> getTemplates() {
        return ResponseEntity.ok(workoutService.getAllTemplates());
    }

    @GetMapping("/plans/{id}")
    public ResponseEntity<WorkoutPlan> getPlan(@PathVariable Long id) {
        return ResponseEntity.ok(workoutService.getPlanById(id));
    }

    @PostMapping("/trainer/plans/assign")
    public ResponseEntity<MemberPlanAssignment> assignPlan(@RequestBody Map<String, Object> body) {
        Long memberId = Long.parseLong(body.get("memberId").toString());
        Long planId = Long.parseLong(body.get("planId").toString());
        LocalDate startDate = body.get("startDate") != null ? LocalDate.parse(body.get("startDate").toString()) : null;
        return ResponseEntity.ok(workoutService.assignPlan(memberId, planId, startDate));
    }

    @GetMapping("/trainer/members/{memberId}/plans")
    public ResponseEntity<List<MemberPlanAssignment>> getMemberAssignments(@PathVariable Long memberId) {
        return ResponseEntity.ok(workoutService.getMemberAssignments(memberId));
    }

    @GetMapping("/member/plan")
    public ResponseEntity<MemberPlanAssignment> getMyPlan() {
        return ResponseEntity.ok(workoutService.getMyActivePlan());
    }

    @PostMapping("/member/workout/log")
    public ResponseEntity<WorkoutLog> logWorkout(@RequestBody WorkoutLog log) {
        return ResponseEntity.ok(workoutService.logWorkout(log));
    }

    @GetMapping("/member/workout/logs")
    public ResponseEntity<List<WorkoutLog>> getMyLogs(@RequestParam(defaultValue = "30") int days) {
        return ResponseEntity.ok(workoutService.getMyLogs(days));
    }

    @GetMapping("/trainer/members/{memberId}/logs")
    public ResponseEntity<List<WorkoutLog>> getMemberLogs(@PathVariable Long memberId,
            @RequestParam(defaultValue = "30") int days) {
        return ResponseEntity.ok(workoutService.getMemberLogs(memberId, days));
    }
}
