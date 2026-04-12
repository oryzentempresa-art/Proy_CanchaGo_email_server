package com.sportsbooking.emailapi.service;

import com.sportsbooking.emailapi.dto.EmailRequest;

public interface EmailService {

    void sendEmail(EmailRequest emailRequest);

}
