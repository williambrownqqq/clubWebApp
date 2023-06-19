package com.masterswork.mail.service;

import com.masterswork.mail.model.Template;
import com.masterswork.mail.repository.TemplateRepository;
import org.springframework.stereotype.Component;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.templateresolver.StringTemplateResolver;
import org.thymeleaf.templateresource.ITemplateResource;

import java.util.Collections;
import java.util.Map;

@Component
public class DatabaseTemplateResolver extends StringTemplateResolver {
    private final TemplateRepository templateRepository;

    public DatabaseTemplateResolver(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
        this.setResolvablePatterns(Collections.singleton("db-*"));
        this.setCacheTTLMs(5*60*1000L);
        this.setCacheable(false);
    }
    
    @Override
    protected ITemplateResource computeTemplateResource(
            IEngineConfiguration configuration, String ownerTemplate, String templateName, Map<String, Object> templateResolutionAttributes) {
        String search = templateName.substring("db-".length());
        Template template = templateRepository.findByName(search).orElse(null);
        return template == null ? null :
                super.computeTemplateResource(configuration, ownerTemplate, template.getBody(), templateResolutionAttributes);
    }
}