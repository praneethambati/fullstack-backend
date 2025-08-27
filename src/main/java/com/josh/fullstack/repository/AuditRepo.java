package com.josh.fullstack.repository;

import com.josh.fullstack.domain.Audit;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepo extends JpaRepository<Audit, Long> {
    Page<Audit> findAllByActorContainsIgnoreCase(String actor, Pageable pg);
}