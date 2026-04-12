package com.sportsbooking.emailapi.config;

import jakarta.mail.internet.InternetAddress;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(MailProperties.class)
public class MailConfig {

    private final MailProperties mailProperties;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailProperties.getHost());
        mailSender.setPort(mailProperties.getPort());
        mailSender.setUsername(mailProperties.getUsername());
        mailSender.setPassword(mailProperties.getPassword());
        mailSender.setDefaultEncoding("UTF-8");

        mailSender.setJavaMailProperties(mailProperties.getProperties());
        log.debug("Configuración SMTP final: {}", mailSender.getJavaMailProperties());

        return mailSender;
    }

    @Bean
    public InternetAddress fromAddress() throws Exception {
        return mailProperties.getDisplayName() != null ?
                new InternetAddress(mailProperties.getFrom(), mailProperties.getDisplayName()) :
                new InternetAddress(mailProperties.getFrom());
    }

}
