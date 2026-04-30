package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// FIELD INJECTION
@Service
public class NotificationServiceField {
    
    @Autowired
    private EmailService emailService;
    
    public String sendEmail(String msg) {
        return "[Field Injection] " + emailService.sendEmail(msg);
    }
}
