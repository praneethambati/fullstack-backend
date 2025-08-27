package com.josh.fullstack.controller;

import com.josh.fullstack.domain.Audit;
import com.josh.fullstack.repository.AuditRepo;
import com.josh.fullstack.dto.AuditReq;
import com.josh.fullstack.dto.AuditRes;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/audits")
public class AuditController {

    private final AuditRepo repo;

    public AuditController(AuditRepo repo) {
        this.repo = repo;
    }

    @GetMapping
    public Page<AuditRes> list(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(required = false) String actor) {
        Pageable pg = PageRequest.of(page, size, Sort.by("whenAt").descending().and(Sort.by("id").descending()));
        Page<Audit> p = (actor == null || actor.isBlank())
                ? repo.findAll(pg)
                : repo.findAllByActorContainsIgnoreCase(actor, pg);
        return p.map(a -> new AuditRes(
                a.getId(), a.getAction(), a.getDetails(), a.getActor(),
                a.getIp(), a.getUserAgent(), a.getWhenAt()
        ));
    }

    // Optional: allow server-side logging from other services
    @PostMapping
    public ResponseEntity<AuditRes> create(@RequestBody AuditReq r, Authentication auth,
                                           @RequestHeader(value = "X-Forwarded-For", required = false) String xff,
                                           @RequestHeader(value = "User-Agent", required = false) String ua) {
        Audit a = new Audit();
        a.setAction(r.action());
        a.setDetails(r.details());
        a.setActor(r.actor() != null ? r.actor() : (auth != null ? auth.getName() : null));
        a.setIp(xff);
        a.setUserAgent(ua);
        a.setWhenAt(LocalDateTime.now());
        a = repo.save(a);
        return ResponseEntity.ok(new AuditRes(
                a.getId(), a.getAction(), a.getDetails(), a.getActor(), a.getIp(), a.getUserAgent(), a.getWhenAt()
        ));
    }
}