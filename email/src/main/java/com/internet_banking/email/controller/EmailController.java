package com.internet_banking.email.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internet_banking.email.DTO.EmailDTO;
import com.internet_banking.email.models.Email;
import com.internet_banking.email.service.EmailService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/emails")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<Email> sendEmail(@RequestBody @Valid EmailDTO data) {
        Email emailEnviado = emailService.sendEmail(data);
        return new ResponseEntity<>(emailEnviado, HttpStatus.CREATED);
    }
}