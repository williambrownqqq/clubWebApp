package com.masterswork.process.api;

import com.masterswork.process.api.dto.process.ProcessResponseDTO;
import com.masterswork.process.api.dto.process.ProcessStartRequest;
import com.masterswork.process.config.principal.UserPrincipal;
import com.masterswork.process.service.ProcessService;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/process")
@RequiredArgsConstructor
public class ProcessController {

    private final ProcessService processService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/start")
    public ResponseEntity<?> startProcess(@Valid @RequestBody ProcessStartRequest body) {
        processService.startOrScheduleRequest(body);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<ProcessResponseDTO>> getAllProcesses(@ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(processService.getAllProcesses(pageable));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/current")
    public ResponseEntity<Page<ProcessResponseDTO>> getProcessesForCurrentUser(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(processService.getProcessInstancesForUser(userPrincipal.getAppUserId(), pageable));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ProcessResponseDTO> getProcessInstanceById(@PathVariable Long id) {
        return ResponseEntity.ok(processService.getProcessInstanceById(id));
    }

}
