package com.josh.fullstack.service;

import com.josh.fullstack.domain.Audit;
import com.josh.fullstack.repository.AuditRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditService {

    private final AuditRepo repo;

    public AuditService(AuditRepo repo) { this.repo = repo; }

    public void log(String action, String details, String actor, String ip, String userAgent) {
        Audit a = new Audit();
        a.setAction(action);
        a.setDetails(details);
        a.setActor(actor);
        a.setIp(ip);
        a.setUserAgent(userAgent);
        a.setWhenAt(LocalDateTime.now());
        repo.save(a);
    }

    // overloads for convenience
    public void log(String action, String details, String actor) {
        log(action, details, actor, null, null);
    }
}