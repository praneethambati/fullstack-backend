package com.josh.fullstack.audit;

import com.josh.fullstack.audit.AuditEvent;
import com.josh.fullstack.audit.AuditEventRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class AuditService {
    private final AuditEventRepo repo;
    public AuditService(AuditEventRepo repo) { this.repo = repo; }

    public void log(String actor, String action, String target, String details, HttpServletRequest req) {
        AuditEvent e = new AuditEvent();
        e.setActor(actor);            // can be null for now
        e.setAction(action);
        e.setTarget(target);
        e.setDetails(details);
        if (req != null) {
            e.setIpAddress(clientIp(req));
            e.setUserAgent(req.getHeader("User-Agent"));
        }
        repo.save(e);
    }

    private String clientIp(HttpServletRequest req) {
        String h = req.getHeader("X-Forwarded-For");
        return (h != null && !h.isBlank()) ? h.split(",")[0].trim() : req.getRemoteAddr();
    }
}