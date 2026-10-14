package com.fitforge.repository;

import com.fitforge.model.User;
import com.fitforge.model.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {
    List<WorkoutPlan> findByTrainer(User trainer);
    List<WorkoutPlan> findByIsTemplate(Boolean isTemplate);
    List<WorkoutPlan> findByTrainerOrIsTemplate(User trainer, Boolean isTemplate);
}
