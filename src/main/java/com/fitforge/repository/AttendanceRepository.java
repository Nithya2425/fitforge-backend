package com.fitforge.repository;

import com.fitforge.model.Attendance;
import com.fitforge.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByMemberAndAttendanceDateBetweenOrderByAttendanceDateDesc(User member, LocalDate start, LocalDate end);
    Optional<Attendance> findByMemberAndAttendanceDate(User member, LocalDate date);
    boolean existsByMemberAndAttendanceDate(User member, LocalDate date);
    long countByMember(User member);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.attendanceDate BETWEEN :start AND :end")
    long countByDateRange(LocalDate start, LocalDate end);
}
