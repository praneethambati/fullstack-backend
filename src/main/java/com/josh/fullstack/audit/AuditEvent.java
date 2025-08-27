// src/main/java/com/josh/fullstack/audit/AuditEvent.java
package com.josh.fullstack.audit;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "audit_events")
public class AuditEvent {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Make actor nullable for now (we'll start filling it later)
    @Column(nullable = true)
    private String actor;           // optional: who performed it (admin); can be null

    @Column(nullable = false)
    private String action;          // e.g. USER_CREATE, USER_UPDATE, LOGIN

    @Column(nullable = false)
    private String target;          // who/what was acted on (email or id)

    @Column(length = 2000)
    private String details;         // JSON or text

    private String ipAddress;
    private String userAgent;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist void prePersist() { createdAt = Instant.now(); }

    // getters/setters ...
    public Long getId() { return id; }
    public String getActor() { return actor; }
    public void setActor(String actor) { this.actor = actor; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public String getTarget() { return target; }
    public void setTarget(String target) { this.target = target; }
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
    public Instant getCreatedAt() { return createdAt; }
}
