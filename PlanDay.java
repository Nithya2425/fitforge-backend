package com.fitforge.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "plan_days")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_plan_id", nullable = false)
    private WorkoutPlan workoutPlan;

    @Column(nullable = false)
    private Integer dayNumber;

    @Column(nullable = false)
    private String dayName;

    @Column(columnDefinition = "TEXT")
    private String focus;

    private Boolean isRestDay = false;

    @OneToMany(mappedBy = "planDay", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    private List<PlanExercise> exercises;
}
