package com.josh.fullstack.repository;

import com.josh.fullstack.domain.CallLog;
import com.josh.fullstack.domain.CallLog.Kind;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallLogRepo extends JpaRepository<CallLog, Long> {
    Page<CallLog> findByCallerContainingIgnoreCase(String q, Pageable pg);
    Page<CallLog> findByKindAndCallerContainingIgnoreCase(Kind kind, String q, Pageable pg);
}