package com.sportsbooking.emailapi.service.impl;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.sportsbooking.emailapi.dto.EmailAttachment;
import com.sportsbooking.emailapi.dto.EmailRequest;
import com.sportsbooking.emailapi.exception.EmailException;
import com.sportsbooking.emailapi.service.EmailService;
import com.sportsbooking.emailapi.service.TemplateService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Base64;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateService templateService;
    private final InternetAddress fromAddress; //MailConfig

    @Override
    public void sendEmail(EmailRequest emailRequest) {
        Mono.fromRunnable(() -> sendEmailSync(emailRequest))
//                .doOnSubscribe(sub -> log.info("Iniciando envío a: {} | Asunto: {}", emailRequest.getTo(), emailRequest.getSubject()))
                .doOnSuccess(unused -> log.info("Correo enviado a: {} | Asunto: {}", emailRequest.getTo(), emailRequest.getSubject()))
                .doOnError(e -> log.error("Error enviando correo a {}: {} | Asunto: {}", emailRequest.getTo(), e.getMessage(), emailRequest.getSubject()))
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(); // Ejecutar en segundo plano sin esperar
    }

    private void sendEmailSync(EmailRequest emailRequest) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromAddress);
            helper.setTo(emailRequest.getTo().toArray(String[]::new));

            if (!CollectionUtils.isEmpty(emailRequest.getCc())) {
                helper.setCc(emailRequest.getCc().toArray(String[]::new));
            }

            if (!CollectionUtils.isEmpty(emailRequest.getBcc())) {
                helper.setBcc(emailRequest.getBcc().toArray(String[]::new));
            }

            String content = getEmailContent(emailRequest);
            helper.setSubject(emailRequest.getSubject());
            helper.setText(content, true);

            if (!CollectionUtils.isEmpty(emailRequest.getAttachments())) {
                for (EmailAttachment attachment : emailRequest.getAttachments()) {
                    byte[] fileBytes = Base64.getDecoder().decode(attachment.getBase64Content());
                    ByteArrayDataSource dataSource = new ByteArrayDataSource(fileBytes, attachment.getContentType());
                    helper.addAttachment(attachment.getFilename(), dataSource);
                }
            }

            mailSender.send(message);
        } catch (Exception e) {
            throw new EmailException("Error al enviar el correo", e);
        }
    }

    private String getEmailContent(EmailRequest emailRequest) {
        if (Objects.isNull(emailRequest.getTemplateName())) {
            throw new EmailException("Debe proporcionar una plantilla");
        }
        return templateService.processTemplate(
                emailRequest.getTemplateName(),
                emailRequest.getTemplateVariables());
    }

}
