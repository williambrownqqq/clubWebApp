package com.masterswork.mail.service.impl;

import com.masterswork.mail.api.dto.template.EmailBodyGenerateFromTemplateDTO;
import com.masterswork.mail.api.dto.template.EmailGeneratedFromTemplateResponseDTO;
import com.masterswork.mail.api.dto.template.TemplateCreateRequestDTO;
import com.masterswork.mail.api.dto.template.TemplateResponseDTO;
import com.masterswork.mail.api.dto.template.TemplateUpdateRequestDTO;
import com.masterswork.mail.api.dto.template.TemplateWithBodyResponseDTO;
import com.masterswork.mail.client.AccountClient;
import com.masterswork.mail.model.Template;
import com.masterswork.mail.repository.TemplateRepository;
import com.masterswork.mail.service.TemplateService;
import com.masterswork.mail.service.exception.TemplateExistsException;
import com.masterswork.mail.service.exception.TemplateNotFoundException;
import com.masterswork.mail.service.mapper.TemplateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;
    private final TemplateMapper templateMapper;
    private final AccountClient accountClient;
    private final SpringTemplateEngine thymeleafTemplateEngine;

    @Override
    public TemplateWithBodyResponseDTO createTemplate(TemplateCreateRequestDTO body) {
        if (templateRepository.existsByName(body.getName())) {
            throw new TemplateExistsException("Template name already taken " + body.getName());
        }
        Template template = templateMapper.from(body);
        return templateMapper.toDTOWithBody(templateRepository.save(template));
    }

    @Override
    public TemplateWithBodyResponseDTO updateTemplate(Long id, TemplateUpdateRequestDTO body) {
        Template template = templateRepository.findById(id)
                .orElseThrow(() -> new TemplateNotFoundException("Could not find template with Id: " + id));

        templateMapper.updateFrom(template, body);
        return templateMapper.toDTOWithBody(templateRepository.save(template));
    }

    @Override
    public TemplateWithBodyResponseDTO getTemplateWithBody(Long id) {
        return templateRepository.findById(id)
                .map(templateMapper::toDTOWithBody)
                .orElseThrow(() -> new TemplateNotFoundException("Could not find template with Id: " + id));
    }

    @Override
    public EmailGeneratedFromTemplateResponseDTO generateEmail(EmailBodyGenerateFromTemplateDTO createDTO) {
        Template template = templateRepository.findById(createDTO.getTemplateId())
                .orElseThrow(() -> new TemplateNotFoundException("Could not find template with Id: " + createDTO.getTemplateId()));

        Context thymeleafContext = new Context();
        Map<String, Object> parameters = Optional.ofNullable(createDTO.getParameters()).orElseGet(HashMap::new);
        parameters.put("from", accountClient.getCurrentUser());
        parameters.put("to", accountClient.getUserById(createDTO.getToId()));
        thymeleafContext.setVariables(parameters);

        return EmailGeneratedFromTemplateResponseDTO.builder()
                .subject(template.getSubject())
                .body(thymeleafTemplateEngine.process(template.getThymleafTemplateName(), thymeleafContext))
                .build();
    }

    @Override
    public Page<TemplateResponseDTO> getAllTemplates(Pageable pageable) {
        return templateRepository.findAll(pageable)
                .map(templateMapper::toDTO);
    }

    @Override
    public void deleteTemplate(Long id) {
        Template template = templateRepository.findById(id)
                .orElseThrow(() -> new TemplateNotFoundException("Could not find template with Id: " + id));
        templateRepository.delete(template);
    }
}
