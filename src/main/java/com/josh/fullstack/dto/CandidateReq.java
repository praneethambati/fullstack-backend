package com.josh.fullstack.dto;

import com.josh.fullstack.domain.Candidate.Status;
import java.time.LocalDate;

public record CandidateReq(
        String name,
        String contact,
        String position,
        int yearsExp,
        Status status,       // enum, not String
        LocalDate appliedOn
) {}
