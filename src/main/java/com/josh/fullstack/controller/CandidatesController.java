package com.josh.fullstack.controller;

import com.josh.fullstack.domain.Candidate;
import com.josh.fullstack.repository.CandidateRepo;
import com.josh.fullstack.dto.CandidateReq;
import com.josh.fullstack.dto.CandidateRes;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/candidates")
public class CandidatesController {

    private final CandidateRepo repo;

    public CandidatesController(CandidateRepo repo) {
        this.repo = repo;
    }

    @GetMapping
    public Page<CandidateRes> list(@RequestParam(defaultValue = "") String q,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "20") int size) {
        Pageable pg = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Candidate> p = (q == null || q.isBlank())
                ? repo.findAll(pg)
                : repo.findByNameContainingIgnoreCaseOrPositionContainingIgnoreCase(q, q, pg);

        return p.map(c -> new CandidateRes(
                c.getId(),
                c.getName(),
                c.getContact(),
                c.getPosition(),
                c.getYearsExp(),
                c.getStatus().name(),
                c.getAppliedOn()
        ));
    }

    @PostMapping
    public ResponseEntity<CandidateRes> create(@RequestBody CandidateReq r) {
        Candidate c = new Candidate();
        c.setName(r.name());
        c.setContact(r.contact());
        c.setPosition(r.position());
        c.setYearsExp(r.yearsExp());
        c.setStatus(r.status());            // already enum
        c.setAppliedOn(r.appliedOn());

        c = repo.save(c);

        CandidateRes body = new CandidateRes(
                c.getId(), c.getName(), c.getContact(), c.getPosition(),
                c.getYearsExp(), c.getStatus().name(), c.getAppliedOn()
        );
        return ResponseEntity.ok(body);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id,
                                             @RequestParam Candidate.Status status) {
        Optional<Candidate> opt = repo.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        Candidate c = opt.get();
        c.setStatus(status);
        repo.save(c);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
