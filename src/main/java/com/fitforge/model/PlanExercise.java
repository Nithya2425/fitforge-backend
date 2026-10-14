package com.fitforge.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "plan_exercises")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_day_id", nullable = false)
    private PlanDay planDay;

    @Column(nullable = false)
    private String exerciseName;

    private Integer sets;

    private Integer reps;

    private Integer durationSeconds;

    private Integer restSeconds;

    private String weight;

    private String notes;

    private Integer orderIndex;

    @Enumerated(EnumType.STRING)
    private MuscleGroup muscleGroup;

    public enum MuscleGroup {
        CHEST, BACK, SHOULDERS, ARMS, LEGS, CORE, CARDIO, FULL_BODY
    }
}
