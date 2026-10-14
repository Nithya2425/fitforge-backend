package com.fitforge.service;

import com.fitforge.exception.BadRequestException;
import com.fitforge.model.Attendance;
import com.fitforge.model.User;
import com.fitforge.repository.AttendanceRepository;
import com.fitforge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public Attendance checkIn(Long memberId) {
        User member = memberId != null
                ? userRepository.findById(memberId).orElseThrow()
                : userService.getCurrentUser();

        LocalDate today = LocalDate.now();
        if (attendanceRepository.existsByMemberAndAttendanceDate(member, today)) {
            throw new BadRequestException("Already checked in today");
        }

        Attendance attendance = Attendance.builder()
                .member(member)
                .attendanceDate(today)
                .checkInTime(LocalTime.now())
                .method(Attendance.Method.QR)
                .build();

        return attendanceRepository.save(attendance);
    }

    public Attendance checkOut() {
        User member = userService.getCurrentUser();
        Attendance attendance = attendanceRepository.findByMemberAndAttendanceDate(member, LocalDate.now())
                .orElseThrow(() -> new BadRequestException("No check-in found for today"));
        attendance.setCheckOutTime(LocalTime.now());
        return attendanceRepository.save(attendance);
    }

    public Attendance markManual(Long memberId) {
        User member = userRepository.findById(memberId).orElseThrow();
        LocalDate today = LocalDate.now();

        if (attendanceRepository.existsByMemberAndAttendanceDate(member, today)) {
            throw new BadRequestException("Already marked present today");
        }

        return attendanceRepository.save(Attendance.builder()
                .member(member)
                .attendanceDate(today)
                .checkInTime(LocalTime.now())
                .method(Attendance.Method.MANUAL)
                .build());
    }

    public List<Attendance> getMyAttendance(int days) {
        User member = userService.getCurrentUser();
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusDays(days);
        return attendanceRepository.findByMemberAndAttendanceDateBetweenOrderByAttendanceDateDesc(member, start, end);
    }

    public List<Attendance> getMemberAttendance(Long memberId, int days) {
        User member = userRepository.findById(memberId).orElseThrow();
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusDays(days);
        return attendanceRepository.findByMemberAndAttendanceDateBetweenOrderByAttendanceDateDesc(member, start, end);
    }

    public long getTodayCount() {
        return attendanceRepository.countByDateRange(LocalDate.now(), LocalDate.now());
    }
}
