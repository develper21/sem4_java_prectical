package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// CONSTRUCTOR INJECTION
@Service
public class NotificationServiceConstructor {
    
    private final EmailService emailService;
    
    @Autowired
    public NotificationServiceConstructor(EmailService emailService) {
        this.emailService = emailService;
    }
    
    public String sendEmail(String msg) {
        return "[Constructor Injection] " + emailService.sendEmail(msg);
    }
}
