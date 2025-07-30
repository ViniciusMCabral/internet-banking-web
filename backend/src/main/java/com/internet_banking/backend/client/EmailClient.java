package com.internet_banking.backend.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Component
public class EmailClient {
    private static final Logger log = LoggerFactory.getLogger(EmailClient.class);

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final String mailFrom;

    public EmailClient(@Value("${email.service.url}") String emailServiceUrl,
                       @Value("${email.from}") String mailFrom) {
        this.restTemplate = new RestTemplate();
        this.baseUrl = emailServiceUrl;
        this.mailFrom = mailFrom;
    }

    public void enviar(String to, String subject, String body) {
        Map<String, String> payload = new HashMap<>();
        payload.put("mailFrom", mailFrom);
        payload.put("mailTo", to);
        payload.put("mailSubject", subject);
        payload.put("mailText", body);
        try {
            restTemplate.postForEntity(baseUrl, payload, Void.class);
        } catch (Exception e) {
            log.error("Erro ao enviar e-mail", e);
        }
    }
}
