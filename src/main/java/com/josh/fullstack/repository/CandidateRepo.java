package com.josh.fullstack.repository;

import com.josh.fullstack.domain.Candidate;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepo extends JpaRepository<Candidate, Long> {
    Page<Candidate> findByNameContainingIgnoreCaseOrPositionContainingIgnoreCase(
            String name, String position, Pageable pageable);
}
