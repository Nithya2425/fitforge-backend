package com.fitforge.repository;

import com.fitforge.model.Membership;
import com.fitforge.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
    Optional<Membership> findByUserAndStatus(User user, Membership.Status status);
    List<Membership> findByUser(User user);
    List<Membership> findByStatus(Membership.Status status);
    List<Membership> findByEndDateBeforeAndStatus(LocalDate date, Membership.Status status);

    @Query("SELECT SUM(m.amount) FROM Membership m WHERE YEAR(m.createdAt) = :year AND MONTH(m.createdAt) = :month")
    BigDecimal sumRevenueByMonth(int year, int month);

    long countByStatus(Membership.Status status);
}
