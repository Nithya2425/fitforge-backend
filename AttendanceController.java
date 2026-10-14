package com.fitforge.controller;

import com.fitforge.model.Attendance;
import com.fitforge.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/member/attendance/checkin")
    public ResponseEntity<Attendance> checkIn() {
        return ResponseEntity.ok(attendanceService.checkIn(null));
    }

    @PostMapping("/member/attendance/checkout")
    public ResponseEntity<Attendance> checkOut() {
        return ResponseEntity.ok(attendanceService.checkOut());
    }

    @GetMapping("/member/attendance")
    public ResponseEntity<List<Attendance>> getMyAttendance(@RequestParam(defaultValue = "30") int days) {
        return ResponseEntity.ok(attendanceService.getMyAttendance(days));
    }

    @PostMapping("/admin/attendance/checkin/{memberId}")
    public ResponseEntity<Attendance> adminCheckIn(@PathVariable Long memberId) {
        return ResponseEntity.ok(attendanceService.checkIn(memberId));
    }

    @PostMapping("/admin/attendance/manual/{memberId}")
    public ResponseEntity<Attendance> markManual(@PathVariable Long memberId) {
        return ResponseEntity.ok(attendanceService.markManual(memberId));
    }

    @GetMapping("/admin/attendance/today")
    public ResponseEntity<Map<String, Long>> getTodayCount() {
        return ResponseEntity.ok(Map.of("count", attendanceService.getTodayCount()));
    }

    @GetMapping("/trainer/members/{memberId}/attendance")
    public ResponseEntity<List<Attendance>> getMemberAttendance(@PathVariable Long memberId,
            @RequestParam(defaultValue = "30") int days) {
        return ResponseEntity.ok(attendanceService.getMemberAttendance(memberId, days));
    }
}
