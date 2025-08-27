package com.josh.fullstack.audit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/audit")
public class AuditController {
    private final AuditEventRepo repo;

    public AuditController(AuditEventRepo repo) { this.repo = repo; }

    // Admin – all events
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<AuditEvent> list(Pageable pageable) {
        return repo.findAll(pageable);
    }

    // Current user – own events
    @GetMapping("/me")
    public Page<AuditEvent> myEvents(@AuthenticationPrincipal UserDetails me, Pageable pageable) {
        return repo.findByActorOrderByCreatedAtDesc(me.getUsername(), pageable);
    }
}