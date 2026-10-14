package com.fitforge.service;

import com.fitforge.exception.ResourceNotFoundException;
import com.fitforge.model.*;
import com.fitforge.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutPlanRepository workoutPlanRepository;
    private final MemberPlanAssignmentRepository assignmentRepository;
    private final WorkoutLogRepository workoutLogRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public WorkoutPlan createPlan(WorkoutPlan plan) {
        User trainer = userService.getCurrentUser();
        plan.setTrainer(trainer);
        return workoutPlanRepository.save(plan);
    }

    public List<WorkoutPlan> getMyPlans() {
        User trainer = userService.getCurrentUser();
        return workoutPlanRepository.findByTrainerOrIsTemplate(trainer, true);
    }

    public List<WorkoutPlan> getAllTemplates() {
        return workoutPlanRepository.findByIsTemplate(true);
    }

    public WorkoutPlan getPlanById(Long id) {
        return workoutPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workout plan not found"));
    }

    public MemberPlanAssignment assignPlan(Long memberId, Long planId, LocalDate startDate) {
        User trainer = userService.getCurrentUser();
        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));
        WorkoutPlan plan = workoutPlanRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found"));

        MemberPlanAssignment assignment = MemberPlanAssignment.builder()
                .member(member)
                .workoutPlan(plan)
                .assignedBy(trainer)
                .startDate(startDate != null ? startDate : LocalDate.now())
                .status(MemberPlanAssignment.Status.ACTIVE)
                .build();

        return assignmentRepository.save(assignment);
    }

    public List<MemberPlanAssignment> getMemberAssignments(Long memberId) {
        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));
        return assignmentRepository.findByMember(member);
    }

    public MemberPlanAssignment getMyActivePlan() {
        User member = userService.getCurrentUser();
        return assignmentRepository.findByMemberAndStatus(member, MemberPlanAssignment.Status.ACTIVE)
                .orElse(null);
    }

    public WorkoutLog logWorkout(WorkoutLog log) {
        User member = userService.getCurrentUser();
        log.setMember(member);
        if (log.getLogDate() == null) log.setLogDate(LocalDate.now());

        WorkoutLog saved = workoutLogRepository.save(log);

        // Update streak
        member.setStreakDays(member.getStreakDays() + 1);
        member.setTotalPoints(member.getTotalPoints() + 10);
        userRepository.save(member);

        return saved;
    }

    public List<WorkoutLog> getMyLogs(int days) {
        User member = userService.getCurrentUser();
        return workoutLogRepository.findByMemberAndLogDateBetweenOrderByLogDateDesc(
                member, LocalDate.now().minusDays(days), LocalDate.now());
    }

    public List<WorkoutLog> getMemberLogs(Long memberId, int days) {
        User member = userRepository.findById(memberId).orElseThrow();
        return workoutLogRepository.findByMemberAndLogDateBetweenOrderByLogDateDesc(
                member, LocalDate.now().minusDays(days), LocalDate.now());
    }

    public long getMyTotalWorkouts() {
        User member = userService.getCurrentUser();
        return workoutLogRepository.countByMember(member);
    }
}
