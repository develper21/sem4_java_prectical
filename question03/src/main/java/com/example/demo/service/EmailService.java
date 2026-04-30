package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    public String sendEmail(String msg) {
        return "Email sent: " + msg;
    }
}
