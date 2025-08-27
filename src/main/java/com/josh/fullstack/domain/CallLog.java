package com.josh.fullstack.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "call_logs")
public class CallLog {

    public enum Kind { INCOMING, OUTGOING, MISSED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Kind kind;

    private String caller;
    private String receiver;     // ✅ correct spelling
    private String purpose;
    private String duration;     // e.g., "15:32" (keep as string for simplicity)

    private LocalDateTime whenAt;

    @Column(length = 2000)
    private String notes;

    // ---- getters ----
    public Long getId() { return id; }
    public Kind getKind() { return kind; }
    public String getCaller() { return caller; }
    public String getReceiver() { return receiver; }
    public String getPurpose() { return purpose; }
    public String getDuration() { return duration; }
    public LocalDateTime getWhenAt() { return whenAt; }
    public String getNotes() { return notes; }

    // ---- setters ----
    public void setId(Long id) { this.id = id; }
    public void setKind(Kind kind) { this.kind = kind; }
    public void setCaller(String caller) { this.caller = caller; }
    public void setReceiver(String receiver) { this.receiver = receiver; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
    public void setDuration(String duration) { this.duration = duration; }
    public void setWhenAt(LocalDateTime whenAt) { this.whenAt = whenAt; }
    public void setNotes(String notes) { this.notes = notes; }
}
