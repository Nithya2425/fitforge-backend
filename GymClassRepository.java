package com.fitforge.repository;

import com.fitforge.model.GymClass;
import com.fitforge.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GymClassRepository extends JpaRepository<GymClass, Long> {
    List<GymClass> findByClassDateGreaterThanEqualOrderByClassDateAscStartTimeAsc(LocalDate date);
    List<GymClass> findByTrainer(User trainer);
    List<GymClass> findByClassDateBetween(LocalDate start, LocalDate end);
}
