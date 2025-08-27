package com.josh.fullstack.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


public record CandidateRes(
        Long id,
        String name,
        String contact,
        String position,
        int yearsExp,
        String status,       // expose as String to the client
        LocalDate appliedOn
) {}