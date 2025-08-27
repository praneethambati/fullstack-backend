package com.josh.fullstack.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audits")
public class Audit {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // e.g. USER_CREATED, LOGIN, ROLE_CHANGED, CANDIDATE_CREATED, INTERVIEW_SCHEDULED, CALL_LOGGED, etc.
    @Column(nullable = false, length = 64)
    private String action;

    // free-form explanation (who/what)
    @Column(nullable = false, length = 1024)
    private String details;

    // who performed it (email/subject)
    @Column(length = 255)
    private String actor;

    // metadata (optional)
    @Column(length = 64)
    private String ip;

    @Column(length = 512)
    private String userAgent;

    @Column(nullable = false)
    private LocalDateTime whenAt = LocalDateTime.now();

    // getters/setters
    public Long getId() { return id; }
    public String getAction() { return action; }
    public String getDetails() { return details; }
    public String getActor() { return actor; }
    public String getIp() { return ip; }
    public String getUserAgent() { return userAgent; }
    public LocalDateTime getWhenAt() { return whenAt; }

    public void setId(Long id) { this.id = id; }
    public void setAction(String action) { this.action = action; }
    public void setDetails(String details) { this.details = details; }
    public void setActor(String actor) { this.actor = actor; }
    public void setIp(String ip) { this.ip = ip; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
    public void setWhenAt(LocalDateTime whenAt) { this.whenAt = whenAt; }
}