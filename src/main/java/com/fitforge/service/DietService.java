package com.fitforge.service;

import com.fitforge.model.DietLog;
import com.fitforge.model.User;
import com.fitforge.repository.DietLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DietService {

    private final DietLogRepository dietLogRepository;
    private final UserService userService;

    public DietLog logMeal(DietLog dietLog) {
        User member = userService.getCurrentUser();
        dietLog.setMember(member);
        if (dietLog.getLogDate() == null) dietLog.setLogDate(LocalDate.now());
        return dietLogRepository.save(dietLog);
    }

    public List<DietLog> getMyLogsForDate(LocalDate date) {
        User member = userService.getCurrentUser();
        return dietLogRepository.findByMemberAndLogDateOrderByCreatedAtAsc(member, date);
    }

    public Integer getTotalCaloriesForDate(LocalDate date) {
        User member = userService.getCurrentUser();
        Integer total = dietLogRepository.sumCaloriesByMemberAndDate(member, date);
        return total != null ? total : 0;
    }

    public void deleteLog(Long id) {
        dietLogRepository.deleteById(id);
    }
}
