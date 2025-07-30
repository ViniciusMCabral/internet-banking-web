package com.internet_banking.email.DTO;

import jakarta.validation.constraints.NotBlank;

public record EmailDTO(@NotBlank(message = "O destinatário (to) é obrigatório.") String to,
						@NotBlank(message = "O assunto (subject) é obrigatório.") String subject,
						@NotBlank(message = "O corpo (body) é obrigatório.") String body) {}