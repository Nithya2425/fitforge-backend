package com.fitforge.repository;

import com.fitforge.model.ClassBooking;
import com.fitforge.model.GymClass;
import com.fitforge.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClassBookingRepository extends JpaRepository<ClassBooking, Long> {
    List<ClassBooking> findByMember(User member);
    List<ClassBooking> findByGymClass(GymClass gymClass);
    Optional<ClassBooking> findByGymClassAndMember(GymClass gymClass, User member);
    boolean existsByGymClassAndMember(GymClass gymClass, User member);
    long countByGymClassAndStatus(GymClass gymClass, ClassBooking.Status status);
}
