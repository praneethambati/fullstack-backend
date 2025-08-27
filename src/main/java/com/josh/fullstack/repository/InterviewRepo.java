package com.josh.fullstack.repository;

import com.josh.fullstack.domain.Interview;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewRepo extends JpaRepository<Interview, Long> {
    Page<Interview> findByCandidateContainingIgnoreCaseOrPositionContainingIgnoreCase(
            String candidate, String position, Pageable pageable);
}