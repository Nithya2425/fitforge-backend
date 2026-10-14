package com.fitforge.controller;

import com.fitforge.model.ClassBooking;
import com.fitforge.model.GymClass;
import com.fitforge.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;

    @PostMapping("/trainer/classes")
    public ResponseEntity<GymClass> createClass(@RequestBody GymClass gymClass) {
        return ResponseEntity.ok(classService.createClass(gymClass));
    }

    @GetMapping("/classes")
    public ResponseEntity<List<GymClass>> getUpcomingClasses() {
        return ResponseEntity.ok(classService.getUpcomingClasses());
    }

    @GetMapping("/trainer/classes")
    public ResponseEntity<List<GymClass>> getMyClasses() {
        return ResponseEntity.ok(classService.getMyClasses());
    }

    @PostMapping("/member/classes/{classId}/book")
    public ResponseEntity<ClassBooking> bookClass(@PathVariable Long classId) {
        return ResponseEntity.ok(classService.bookClass(classId));
    }

    @DeleteMapping("/member/classes/{classId}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long classId) {
        classService.cancelBooking(classId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/member/classes/bookings")
    public ResponseEntity<List<ClassBooking>> getMyBookings() {
        return ResponseEntity.ok(classService.getMyBookings());
    }

    @GetMapping("/trainer/classes/{classId}/attendees")
    public ResponseEntity<List<ClassBooking>> getAttendees(@PathVariable Long classId) {
        return ResponseEntity.ok(classService.getClassAttendees(classId));
    }

    @PatchMapping("/trainer/classes/{classId}/cancel")
    public ResponseEntity<GymClass> cancelClass(@PathVariable Long classId) {
        return ResponseEntity.ok(classService.cancelClass(classId));
    }
}
