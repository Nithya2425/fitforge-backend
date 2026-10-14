package com.fitforge.repository;

import com.fitforge.model.TrainerAssignment;
import com.fitforge.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerAssignmentRepository extends JpaRepository<TrainerAssignment, Long> {
    List<TrainerAssignment> findByTrainerAndStatus(User trainer, TrainerAssignment.Status status);
    List<TrainerAssignment> findByMember(User member);
    Optional<TrainerAssignment> findByMemberAndStatus(User member, TrainerAssignment.Status status);
    long countByTrainerAndStatus(User trainer, TrainerAssignment.Status status);
}
