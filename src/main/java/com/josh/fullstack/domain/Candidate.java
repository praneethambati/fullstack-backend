package com.josh.fullstack.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "candidates")
public class Candidate {

    public enum Status { NEW, INTERVIEWED, HIRED, REJECTED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String contact;      // email or phone
    private String position;     // e.g. "Backend Developer"
    private int yearsExp;        // numeric, not String

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate appliedOn;

    // ---- getters ----
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getContact() { return contact; }
    public String getPosition() { return position; }
    public int getYearsExp() { return yearsExp; }
    public Status getStatus() { return status; }
    public LocalDate getAppliedOn() { return appliedOn; }

    // ---- setters ----
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setContact(String contact) { this.contact = contact; }
    public void setPosition(String position) { this.position = position; }
    public void setYearsExp(int yearsExp) { this.yearsExp = yearsExp; }
    public void setStatus(Status status) { this.status = status; }
    public void setAppliedOn(LocalDate appliedOn) { this.appliedOn = appliedOn; }
}
