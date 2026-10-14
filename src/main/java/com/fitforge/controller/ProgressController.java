package com.fitforge.controller;

import com.fitforge.model.ProgressEntry;
import com.fitforge.service.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/member/progress")
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressService progressService;

    @PostMapping
    public ResponseEntity<ProgressEntry> addEntry(@RequestBody ProgressEntry entry) {
        return ResponseEntity.ok(progressService.addEntry(entry));
    }

    @GetMapping
    public ResponseEntity<List<ProgressEntry>> getMyProgress() {
        return ResponseEntity.ok(progressService.getMyProgress());
    }

    @GetMapping("/latest")
    public ResponseEntity<ProgressEntry> getLatest() {
        return ResponseEntity.ok(progressService.getLatestEntry());
    }
}
