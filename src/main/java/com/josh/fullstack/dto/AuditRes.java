package com.josh.fullstack.dto;

import java.time.LocalDateTime;

public record AuditRes(
        Long id,
        String action,
        String details,
        String actor,
        String ip,
        String userAgent,
        LocalDateTime whenAt
) {}