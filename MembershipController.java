package com.fitforge.controller;

import com.fitforge.model.Membership;
import com.fitforge.model.Payment;
import com.fitforge.service.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MembershipController {

    private final MembershipService membershipService;

    @PostMapping("/admin/memberships")
    public ResponseEntity<Membership> createMembership(@RequestBody Map<String, String> body) {
        Long memberId = Long.parseLong(body.get("memberId"));
        Membership.PlanType planType = Membership.PlanType.valueOf(body.get("planType").toUpperCase());
        Payment.PaymentMethod method = body.get("paymentMethod") != null
                ? Payment.PaymentMethod.valueOf(body.get("paymentMethod").toUpperCase())
                : Payment.PaymentMethod.CASH;
        return ResponseEntity.ok(membershipService.createMembership(memberId, planType, method));
    }

    @GetMapping("/member/membership")
    public ResponseEntity<Membership> getMyMembership() {
        return ResponseEntity.ok(membershipService.getMyMembership());
    }

    @GetMapping("/admin/members/{memberId}/memberships")
    public ResponseEntity<List<Membership>> getMemberMemberships(@PathVariable Long memberId) {
        return ResponseEntity.ok(membershipService.getMemberMemberships(memberId));
    }

    @GetMapping("/member/payments")
    public ResponseEntity<List<Payment>> getMyPayments() {
        return ResponseEntity.ok(membershipService.getMyPayments());
    }

    @GetMapping("/admin/memberships/expiring")
    public ResponseEntity<List<Membership>> getExpiringMemberships(@RequestParam(defaultValue = "7") int days) {
        return ResponseEntity.ok(membershipService.getExpiringMemberships(days));
    }
}
