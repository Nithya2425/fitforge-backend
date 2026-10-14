package com.fitforge.service;

import com.fitforge.exception.BadRequestException;
import com.fitforge.exception.ResourceNotFoundException;
import com.fitforge.model.ClassBooking;
import com.fitforge.model.GymClass;
import com.fitforge.model.User;
import com.fitforge.repository.ClassBookingRepository;
import com.fitforge.repository.GymClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassService {

    private final GymClassRepository classRepository;
    private final ClassBookingRepository bookingRepository;
    private final UserService userService;

    public GymClass createClass(GymClass gymClass) {
        User trainer = userService.getCurrentUser();
        gymClass.setTrainer(trainer);
        return classRepository.save(gymClass);
    }

    public List<GymClass> getUpcomingClasses() {
        return classRepository.findByClassDateGreaterThanEqualOrderByClassDateAscStartTimeAsc(LocalDate.now());
    }

    public List<GymClass> getMyClasses() {
        User trainer = userService.getCurrentUser();
        return classRepository.findByTrainer(trainer);
    }

    public ClassBooking bookClass(Long classId) {
        User member = userService.getCurrentUser();
        GymClass gymClass = classRepository.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));

        if (bookingRepository.existsByGymClassAndMember(gymClass, member)) {
            throw new BadRequestException("Already booked this class");
        }

        long confirmedCount = bookingRepository.countByGymClassAndStatus(gymClass, ClassBooking.Status.CONFIRMED);
        if (gymClass.getMaxCapacity() != null && confirmedCount >= gymClass.getMaxCapacity()) {
            throw new BadRequestException("Class is full");
        }

        return bookingRepository.save(ClassBooking.builder()
                .gymClass(gymClass)
                .member(member)
                .status(ClassBooking.Status.CONFIRMED)
                .build());
    }

    public void cancelBooking(Long classId) {
        User member = userService.getCurrentUser();
        GymClass gymClass = classRepository.findById(classId).orElseThrow();
        ClassBooking booking = bookingRepository.findByGymClassAndMember(gymClass, member)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        booking.setStatus(ClassBooking.Status.CANCELLED);
        bookingRepository.save(booking);
    }

    public List<ClassBooking> getMyBookings() {
        User member = userService.getCurrentUser();
        return bookingRepository.findByMember(member);
    }

    public List<ClassBooking> getClassAttendees(Long classId) {
        GymClass gymClass = classRepository.findById(classId).orElseThrow();
        return bookingRepository.findByGymClass(gymClass);
    }

    public GymClass cancelClass(Long classId) {
        GymClass gymClass = classRepository.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found"));
        gymClass.setIsCancelled(true);
        return classRepository.save(gymClass);
    }
}
