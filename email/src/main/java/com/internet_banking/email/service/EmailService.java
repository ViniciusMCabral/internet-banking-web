package com.internet_banking.email.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.internet_banking.email.DTO.EmailDTO;
import com.internet_banking.email.models.Email;
import com.internet_banking.email.models.EmailStatus;
import com.internet_banking.email.repository.EmailRepository;

@Service
public class EmailService {

    private final EmailRepository emailRepository;
    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;

    public EmailService(EmailRepository emailRepository, JavaMailSender emailSender) {
        this.emailRepository = emailRepository;
        this.emailSender = emailSender;
    }

    public Email sendEmail(EmailDTO dto) {
        Email email = new Email(dto);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailFrom); 
            message.setTo(dto.to()); 
            message.setSubject(dto.subject());
            message.setText(dto.body());

            emailSender.send(message);

            email.setStatus(EmailStatus.SENT); 
        } catch (MailException e) {
            email.setStatus(EmailStatus.ERROR);
        } finally {
            emailRepository.save(email);
        }
        return email;
    }
}