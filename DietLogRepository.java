package com.fitforge.repository;

import com.fitforge.model.DietLog;
import com.fitforge.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DietLogRepository extends JpaRepository<DietLog, Long> {
    List<DietLog> findByMemberAndLogDateOrderByCreatedAtAsc(User member, LocalDate date);
    List<DietLog> findByMemberAndLogDateBetween(User member, LocalDate start, LocalDate end);

    @Query("SELECT SUM(d.calories) FROM DietLog d WHERE d.member = :member AND d.logDate = :date")
    Integer sumCaloriesByMemberAndDate(User member, LocalDate date);
}
