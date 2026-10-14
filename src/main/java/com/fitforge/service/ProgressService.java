package com.fitforge.service;

import com.fitforge.model.ProgressEntry;
import com.fitforge.model.User;
import com.fitforge.repository.ProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressService {

    private final ProgressRepository progressRepository;
    private final UserService userService;

    public ProgressEntry addEntry(ProgressEntry entry) {
        User member = userService.getCurrentUser();
        entry.setMember(member);
        return progressRepository.save(entry);
    }

    public List<ProgressEntry> getMyProgress() {
        return progressRepository.findByMemberOrderByEntryDateDesc(userService.getCurrentUser());
    }

    public ProgressEntry getLatestEntry() {
        return progressRepository.findTopByMemberOrderByEntryDateDesc(userService.getCurrentUser()).orElse(null);
    }
}
