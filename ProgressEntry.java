package com.fitforge.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "progress_entries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgressEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private User member;

    @Column(nullable = false)
    private LocalDate entryDate;

    private Double weightKg;

    private Double bodyFatPercent;

    private Double chestCm;

    private Double waistCm;

    private Double hipCm;

    private Double armCm;

    private Double thighCm;

    private Double bmi;

    private String progressPhoto;

    @Column(columnDefinition = "TEXT")
    private String notes;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
