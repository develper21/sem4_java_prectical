package com.example.demo.controller;

import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DIController {
    
    @Autowired
    private NotificationServiceField fieldService;
    
    @Autowired
    private NotificationServiceConstructor constructorService;
    
    @Autowired
    private NotificationServiceSetter setterService;
    
    @GetMapping("/field")
    public String testFieldInjection() {
        return fieldService.sendEmail("Test");
    }
    
    @GetMapping("/constructor")
    public String testConstructorInjection() {
        return constructorService.sendEmail("Test");
    }
    
    @GetMapping("/setter")
    public String testSetterInjection() {
        return setterService.sendEmail("Test");
    }
    
    @GetMapping("/all")
    public String testAll() {
        StringBuilder sb = new StringBuilder();
        sb.append(fieldService.sendEmail("Test")).append("\n");
        sb.append(constructorService.sendEmail("Test")).append("\n");
        sb.append(setterService.sendEmail("Test"));
        return sb.toString();
    }
}
