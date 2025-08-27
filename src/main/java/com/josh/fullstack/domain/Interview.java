package com.josh.fullstack.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "interviews")
public class Interview {

    public enum Type { VIDEO, PHONE, IN_PERSON }
    public enum Status { SCHEDULED, COMPLETED, IN_PROGRESS, CANCELLED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String candidate;        // e.g., "Sarah Johnson"
    private String position;         // e.g., "Frontend Developer"

    @Enumerated(EnumType.STRING)
    private Type type;

    private LocalDateTime startAt;   // date + time

    private String interviewer;      // e.g., "John Smith"

    @Enumerated(EnumType.STRING)
    private Status status;

    // ----- getters -----
    public Long getId() { return id; }
    public String getCandidate() { return candidate; }
    public String getPosition() { return position; }
    public Type getType() { return type; }
    public LocalDateTime getStartAt() { return startAt; }
    public String getInterviewer() { return interviewer; }
    public Status getStatus() { return status; }

    // ----- setters -----
    public void setId(Long id) { this.id = id; }
    public void setCandidate(String candidate) { this.candidate = candidate; }
    public void setPosition(String position) { this.position = position; }
    public void setType(Type type) { this.type = type; }
    public void setStartAt(LocalDateTime startAt) { this.startAt = startAt; }
    public void setInterviewer(String interviewer) { this.interviewer = interviewer; }
    public void setStatus(Status status) { this.status = status; }
}
