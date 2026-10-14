package com.fitforge.controller;

import com.fitforge.model.DietLog;
import com.fitforge.service.DietService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/member/diet")
@RequiredArgsConstructor
public class DietController {

    private final DietService dietService;

    @PostMapping("/log")
    public ResponseEntity<DietLog> logMeal(@RequestBody DietLog dietLog) {
        return ResponseEntity.ok(dietService.logMeal(dietLog));
    }

    @GetMapping("/logs")
    public ResponseEntity<List<DietLog>> getLogs(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(dietService.getMyLogsForDate(date != null ? date : LocalDate.now()));
    }

    @GetMapping("/calories")
    public ResponseEntity<Map<String, Integer>> getDailyCalories(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(Map.of("total", dietService.getTotalCaloriesForDate(date != null ? date : LocalDate.now())));
    }

    @DeleteMapping("/logs/{id}")
    public ResponseEntity<Void> deleteLog(@PathVariable Long id) {
        dietService.deleteLog(id);
        return ResponseEntity.noContent().build();
    }
}
