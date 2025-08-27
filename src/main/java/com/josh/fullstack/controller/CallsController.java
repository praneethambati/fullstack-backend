package com.josh.fullstack.controller;

import com.josh.fullstack.domain.CallLog;
import com.josh.fullstack.domain.CallLog.Kind;
import com.josh.fullstack.repository.CallLogRepo;
import com.josh.fullstack.dto.CallReq;
import com.josh.fullstack.dto.CallRes;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calls")
public class CallsController {

    private final CallLogRepo repo;

    public CallsController(CallLogRepo repo) {
        this.repo = repo;
    }

    @GetMapping
    public Page<CallRes> list(@RequestParam(defaultValue = "") String q,
                              @RequestParam(defaultValue = "ALL") String type,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "20") int size) {

        Pageable pg = PageRequest.of(page, size, Sort.by("whenAt").descending());

        Page<CallLog> p;
        if (!"ALL".equalsIgnoreCase(type)) {
            Kind k = Kind.valueOf(type.toUpperCase());
            p = repo.findByKindAndCallerContainingIgnoreCase(k, q, pg);
        } else {
            p = repo.findByCallerContainingIgnoreCase(q, pg);
        }

        return p.map(c -> new CallRes(
                c.getId(),
                c.getKind(),
                c.getCaller(),
                c.getReceiver(),
                c.getPurpose(),
                c.getDuration(),
                c.getWhenAt(),
                c.getNotes()
        ));
    }

    @PostMapping
    public ResponseEntity<CallRes> create(@RequestBody CallReq r) {
        CallLog c = new CallLog();
        c.setKind(r.kind());
        c.setCaller(r.caller());
        c.setReceiver(r.receiver());
        c.setPurpose(r.purpose());
        c.setDuration(r.duration());
        c.setWhenAt(r.whenAt());
        c.setNotes(r.notes());

        c = repo.save(c);

        CallRes body = new CallRes(
                c.getId(), c.getKind(), c.getCaller(), c.getReceiver(),
                c.getPurpose(), c.getDuration(), c.getWhenAt(), c.getNotes()
        );
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
