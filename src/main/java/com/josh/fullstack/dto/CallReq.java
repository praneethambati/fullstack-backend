package com.josh.fullstack.dto;

import com.josh.fullstack.domain.CallLog.Kind;
import java.time.LocalDateTime;

import com.josh.fullstack.domain.CallLog.Kind;
import java.time.LocalDateTime;

public record CallReq(
        Kind kind,
        String caller,
        String receiver,
        String purpose,
        String duration,
        LocalDateTime whenAt,
        String notes
) {}