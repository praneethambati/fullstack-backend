package com.josh.fullstack.controller;

import com.josh.fullstack.domain.Interview;
import com.josh.fullstack.repository.InterviewRepo;
import com.josh.fullstack.dto.InterviewReq;
import com.josh.fullstack.dto.InterviewRes;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/interviews")
public class InterviewsController {

    private final InterviewRepo repo;

    public InterviewsController(InterviewRepo repo) {
        this.repo = repo;
    }

    @GetMapping
    public Page<InterviewRes> list(@RequestParam(defaultValue = "") String q,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "20") int size) {
        Pageable pg = PageRequest.of(page, size, Sort.by("startAt").descending());
        Page<Interview> p = (q == null || q.isBlank())
                ? repo.findAll(pg)
                : repo.findByCandidateContainingIgnoreCaseOrPositionContainingIgnoreCase(q, q, pg);

        return p.map(i -> new InterviewRes(
                i.getId(),
                i.getCandidate(),
                i.getPosition(),
                i.getType(),
                i.getStartAt(),
                i.getInterviewer(),
                i.getStatus()
        ));
    }

    @PostMapping
    public ResponseEntity<InterviewRes> create(@RequestBody InterviewReq r) {
        Interview i = new Interview();
        i.setCandidate(r.candidate());
        i.setPosition(r.position());
        i.setType(r.type());
        i.setStartAt(r.startAt());
        i.setInterviewer(r.interviewer());
        i.setStatus(r.status());

        i = repo.save(i);

        InterviewRes body = new InterviewRes(
                i.getId(), i.getCandidate(), i.getPosition(), i.getType(),
                i.getStartAt(), i.getInterviewer(), i.getStatus()
        );
        return ResponseEntity.ok(body);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id,
                                             @RequestParam Interview.Status status) {
        Optional<Interview> opt = repo.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        Interview i = opt.get();
        i.setStatus(status);
        repo.save(i);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
