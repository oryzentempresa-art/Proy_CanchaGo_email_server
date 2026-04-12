package com.sportsbooking.emailapi.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Properties;

@Data
@Validated
@ConfigurationProperties(prefix = "spring.mail")
public class MailProperties {
    @NotBlank
    private String host;

    @Positive
    private int port;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String from;

    private String displayName;

    private final Properties properties = new Properties();
}