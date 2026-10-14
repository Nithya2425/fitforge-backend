package com.fitforge.service;

import com.fitforge.exception.ResourceNotFoundException;
import com.fitforge.model.Membership;
import com.fitforge.model.Payment;
import com.fitforge.model.User;
import com.fitforge.repository.MembershipRepository;
import com.fitforge.repository.PaymentRepository;
import com.fitforge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MembershipService {

    private final MembershipRepository membershipRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    private static final Map<Membership.PlanType, BigDecimal> PRICES = Map.of(
            Membership.PlanType.BASIC, new BigDecimal("999"),
            Membership.PlanType.STANDARD, new BigDecimal("1999"),
            Membership.PlanType.PREMIUM, new BigDecimal("3499"),
            Membership.PlanType.ELITE, new BigDecimal("5999")
    );

    private static final Map<Membership.PlanType, Integer> DURATION_MONTHS = Map.of(
            Membership.PlanType.BASIC, 1,
            Membership.PlanType.STANDARD, 3,
            Membership.PlanType.PREMIUM, 6,
            Membership.PlanType.ELITE, 12
    );

    public Membership createMembership(Long memberId, Membership.PlanType planType, Payment.PaymentMethod paymentMethod) {
        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));

        // Expire old membership if exists
        membershipRepository.findByUserAndStatus(member, Membership.Status.ACTIVE)
                .ifPresent(m -> {
                    m.setStatus(Membership.Status.EXPIRED);
                    membershipRepository.save(m);
                });

        BigDecimal price = PRICES.get(planType);
        int months = DURATION_MONTHS.get(planType);

        Membership membership = Membership.builder()
                .user(member)
                .planType(planType)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(months))
                .amount(price)
                .status(Membership.Status.ACTIVE)
                .build();

        Membership saved = membershipRepository.save(membership);

        // Record payment
        paymentRepository.save(Payment.builder()
                .member(member)
                .membership(saved)
                .amount(price)
                .status(Payment.Status.COMPLETED)
                .paymentMethod(paymentMethod)
                .description("Membership: " + planType.name())
                .paidAt(LocalDateTime.now())
                .build());

        return saved;
    }

    public Membership getMyMembership() {
        User member = userService.getCurrentUser();
        return membershipRepository.findByUserAndStatus(member, Membership.Status.ACTIVE).orElse(null);
    }

    public List<Membership> getMemberMemberships(Long memberId) {
        User member = userRepository.findById(memberId).orElseThrow();
        return membershipRepository.findByUser(member);
    }

    public List<Payment> getMyPayments() {
        User member = userService.getCurrentUser();
        return paymentRepository.findByMemberOrderByCreatedAtDesc(member);
    }

    public BigDecimal getMonthlyRevenue() {
        LocalDateTime start = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime end = LocalDateTime.now();
        BigDecimal revenue = paymentRepository.sumRevenueBetween(start, end);
        return revenue != null ? revenue : BigDecimal.ZERO;
    }

    public List<Membership> getExpiringMemberships(int days) {
        return membershipRepository.findByEndDateBeforeAndStatus(
                LocalDate.now().plusDays(days), Membership.Status.ACTIVE);
    }

    public long countActiveMembers() {
        return membershipRepository.countByStatus(Membership.Status.ACTIVE);
    }
}
