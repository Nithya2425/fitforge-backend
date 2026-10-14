package com.fitforge.repository;

import com.fitforge.model.User;
import com.fitforge.model.WorkoutLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, Long> {
    List<WorkoutLog> findByMemberOrderByLogDateDesc(User member);
    List<WorkoutLog> findByMemberAndLogDateBetweenOrderByLogDateDesc(User member, LocalDate start, LocalDate end);
    boolean existsByMemberAndLogDate(User member, LocalDate date);
    long countByMember(User member);

    @Query("SELECT COUNT(DISTINCT w.logDate) FROM WorkoutLog w WHERE w.member = :member AND w.logDate BETWEEN :start AND :end")
    long countDistinctDays(User member, LocalDate start, LocalDate end);
}
