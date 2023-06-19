package com.masterswork.mail.api;

import com.masterswork.mail.api.dto.template.EmailBodyGenerateFromTemplateDTO;
import com.masterswork.mail.api.dto.template.EmailGeneratedFromTemplateResponseDTO;
import com.masterswork.mail.api.dto.template.TemplateCreateRequestDTO;
import com.masterswork.mail.api.dto.template.TemplateResponseDTO;
import com.masterswork.mail.api.dto.template.TemplateUpdateRequestDTO;
import com.masterswork.mail.api.dto.template.TemplateWithBodyResponseDTO;
import com.masterswork.mail.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TemplateWithBodyResponseDTO> createTemplate(@RequestBody TemplateCreateRequestDTO body) {
        return ResponseEntity.ok(templateService.createTemplate(body));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TemplateWithBodyResponseDTO> updateTemplate(
            @PathVariable Long id, @RequestBody TemplateUpdateRequestDTO body) {
        return ResponseEntity.ok(templateService.updateTemplate(id, body));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TemplateWithBodyResponseDTO> getTemplate(@PathVariable Long id) {
        return ResponseEntity.ok(templateService.getTemplateWithBody(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<TemplateResponseDTO>> getAllTemplates(
            @ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(templateService.getAllTemplates(pageable));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteTemplate(@PathVariable Long id) {
        templateService.deleteTemplate(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/generate")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<EmailGeneratedFromTemplateResponseDTO> generateEmailFromTemplate(
            @RequestBody EmailBodyGenerateFromTemplateDTO body) {
        return ResponseEntity.ok(templateService.generateEmail(body));
    }
}
