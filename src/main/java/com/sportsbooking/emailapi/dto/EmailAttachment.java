package com.sportsbooking.emailapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class EmailAttachment implements Serializable {

    @NotNull
    private String filename;

    @NotNull
    private String contentType; // ej: "application/pdf"

    @NotNull
    private String base64Content;

}