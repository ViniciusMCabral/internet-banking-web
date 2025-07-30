package com.internet_banking.backend.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class EmailClient { 

    private static final Logger log = LoggerFactory.getLogger(EmailClient.class);

    private final RestTemplate restTemplate;
    private final String emailServiceUrl;

    public EmailClient(RestTemplate restTemplate,
                       @Value("${microservico.email.url}") String emailServiceUrl) {
        this.restTemplate = restTemplate;
        this.emailServiceUrl = emailServiceUrl;
    }

    public void enviar(String para, String assunto, String corpo) {
        Map<String, String> payload = new HashMap<>();
        payload.put("to", para);
        payload.put("subject", assunto);
        payload.put("body", corpo);

        try {
            restTemplate.postForEntity(emailServiceUrl, payload, Void.class);
            log.info("Requisição de envio de e-mail para {} enviada com sucesso.", para);
        } catch (Exception e) {
            log.error("Erro ao enviar requisição para o microserviço de e-mail", e);
        }
    }
}