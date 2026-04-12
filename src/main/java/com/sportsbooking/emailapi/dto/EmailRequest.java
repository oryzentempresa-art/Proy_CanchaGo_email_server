package com.sportsbooking.emailapi.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class EmailRequest implements Serializable {
    @NotEmpty(message = "El campo 'to' no puede estar vacío")
    private List<String> to;

    private List<String> cc;
    private List<String> bcc;

    @NotNull(message = "El campo 'subject' no puede ser nulo")
    private String subject;

    private String templateName;
    private Map<String, Object> templateVariables;

    // Lista de adjuntos (opcionales)
    private List<EmailAttachment> attachments;
}
