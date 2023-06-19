package com.masterswork.mail.api;

import com.masterswork.mail.api.dto.mail.EmailResponseDTO;
import com.masterswork.mail.api.dto.mail.EmailSendDTO;
import com.masterswork.mail.api.dto.mail.EmailSendFromTemplateDTO;
import com.masterswork.mail.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/mails")
@RequiredArgsConstructor
public class MailController {

    private final EmailService emailService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<EmailResponseDTO> sendEmail(@Valid @RequestBody EmailSendDTO emailSendDTO) {
        return ResponseEntity.ok(emailService.prepareAndSend(emailSendDTO));
    }

    @PostMapping("/template/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<EmailResponseDTO> sendEmailFromTemplate(
            @PathVariable Long id, @Valid @RequestBody EmailSendFromTemplateDTO emailSendDTO) {
        return ResponseEntity.ok(emailService.prepareAndSendFromTemplate(id, emailSendDTO));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<EmailResponseDTO>> getAllEmails(
            @RequestParam(name = "username", required = false) String username,
            @ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        Page<EmailResponseDTO> allEmails =  username == null ?
                emailService.getAllEmails(pageable) : emailService.getAllEmailsByUser(username, pageable);
        return ResponseEntity.ok(allEmails);
    }

    @GetMapping("/current")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<EmailResponseDTO>> getAllEmailsCurrentUser(
            Authentication authentication, @ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(emailService.getAllEmailsByUser(authentication.getName(), pageable));
    }
}
