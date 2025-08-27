package com.josh.fullstack.audit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuditEventRepo extends JpaRepository<AuditEvent, Long> {
    Page<AuditEvent> findByActorOrderByCreatedAtDesc(String actor, Pageable p);
}