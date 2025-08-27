package com.josh.fullstack.dto;

public record AuditReq(
        String action,
        String details,
        String actor,     // optional if server derives from auth
        String ip,
        String userAgent
) {}
