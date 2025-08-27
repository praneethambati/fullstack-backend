package com.josh.fullstack.dto;

import com.josh.fullstack.domain.Interview.Type;
import com.josh.fullstack.domain.Interview.Status;
import java.time.LocalDateTime;

public record InterviewRes(
        Long id,
        String candidate,
        String position,
        Type type,
        LocalDateTime startAt,
        String interviewer,
        Status status
) {}