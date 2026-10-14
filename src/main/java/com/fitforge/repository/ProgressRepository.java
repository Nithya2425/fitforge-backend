package com.fitforge.repository;

import com.fitforge.model.ProgressEntry;
import com.fitforge.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressRepository extends JpaRepository<ProgressEntry, Long> {
    List<ProgressEntry> findByMemberOrderByEntryDateDesc(User member);
    Optional<ProgressEntry> findTopByMemberOrderByEntryDateDesc(User member);
}
