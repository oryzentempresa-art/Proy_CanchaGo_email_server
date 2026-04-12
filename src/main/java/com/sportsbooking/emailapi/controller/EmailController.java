package com.sportsbooking.emailapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.sportsbooking.emailapi.dto.EmailRequest;
import com.sportsbooking.emailapi.dto.EmailResponse;
import com.sportsbooking.emailapi.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    public Mono<ResponseEntity<EmailResponse>> sendEmail(@Valid @RequestBody EmailRequest request) {
        emailService.sendEmail(request); // No retornamos el Mono
        return Mono.just(ResponseEntity.accepted().build()); // HTTP inmediato
    }

}
