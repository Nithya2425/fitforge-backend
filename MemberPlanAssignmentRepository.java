package com.fitforge.repository;

import com.fitforge.model.MemberPlanAssignment;
import com.fitforge.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberPlanAssignmentRepository extends JpaRepository<MemberPlanAssignment, Long> {
    List<MemberPlanAssignment> findByMember(User member);
    Optional<MemberPlanAssignment> findByMemberAndStatus(User member, MemberPlanAssignment.Status status);
    List<MemberPlanAssignment> findByWorkoutPlan_Trainer(User trainer);
}
