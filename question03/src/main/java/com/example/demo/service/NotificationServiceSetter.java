package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// SETTER INJECTION
@Service
public class NotificationServiceSetter {
    
    private EmailService emailService;
    
    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
    
    public String sendEmail(String msg) {
        return "[Setter Injection] " + emailService.sendEmail(msg);
    }
}
