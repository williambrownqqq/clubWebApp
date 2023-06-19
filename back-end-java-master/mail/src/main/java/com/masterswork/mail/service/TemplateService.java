package com.masterswork.mail.service;

import com.masterswork.mail.api.dto.template.EmailBodyGenerateFromTemplateDTO;
import com.masterswork.mail.api.dto.template.EmailGeneratedFromTemplateResponseDTO;
import com.masterswork.mail.api.dto.template.TemplateCreateRequestDTO;
import com.masterswork.mail.api.dto.template.TemplateResponseDTO;
import com.masterswork.mail.api.dto.template.TemplateUpdateRequestDTO;
import com.masterswork.mail.api.dto.template.TemplateWithBodyResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TemplateService {

    TemplateWithBodyResponseDTO createTemplate(TemplateCreateRequestDTO body);

    TemplateWithBodyResponseDTO updateTemplate(Long id, TemplateUpdateRequestDTO body);

    TemplateWithBodyResponseDTO getTemplateWithBody(Long id);

    EmailGeneratedFromTemplateResponseDTO generateEmail(EmailBodyGenerateFromTemplateDTO body);

    Page<TemplateResponseDTO> getAllTemplates(Pageable pageable);

    void deleteTemplate(Long id);
}
