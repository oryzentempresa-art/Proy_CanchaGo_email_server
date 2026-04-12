package com.sportsbooking.emailapi.service.impl;

import lombok.RequiredArgsConstructor;
import com.sportsbooking.emailapi.exception.EmailException;
import com.sportsbooking.emailapi.service.TemplateService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final TemplateEngine templateEngine;
    private final ResourceLoader resourceLoader;

    @Override
    public String processTemplate(String templateName, Map<String, Object> variables) {
        if (variables == null || variables.isEmpty()) {
            // Carga HTML estático
            return loadStaticTemplate(templateName);
        } else {
            // Procesa con Thymeleaf
            try {
                Context context = new Context();
                context.setVariables(variables);
                return templateEngine.process(templateName, context);
            } catch (Exception e) {
                throw new EmailException("Error al procesar la plantilla: " + templateName, e);
            }
        }
    }

    private String loadStaticTemplate(String templateName) {
        Resource resource = resourceLoader.getResource("classpath:templates/" + templateName);
        try {
            return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new EmailException("Error al cargar plantilla: " + templateName, e);
        }
    }

}
