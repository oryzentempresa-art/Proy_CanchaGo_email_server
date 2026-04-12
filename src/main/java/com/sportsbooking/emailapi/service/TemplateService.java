package com.sportsbooking.emailapi.service;

import java.util.Map;

public interface TemplateService {

    String processTemplate(String templateName, Map<String, Object> variables);

}
