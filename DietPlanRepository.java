package com.fitforge.repository;

import com.fitforge.model.DietPlan;
import com.fitforge.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DietPlanRepository extends JpaRepository<DietPlan, Long> {
    List<DietPlan> findByCreatedBy(User createdBy);
    List<DietPlan> findByIsTemplate(Boolean isTemplate);
}
