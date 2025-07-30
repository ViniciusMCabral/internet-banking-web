package com.internet_banking.backend.dtos; 

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OperacaoRequestDTO(@NotNull(message = "O valor não pode ser nulo.") @Positive(message = "O valor deve ser positivo.")
		BigDecimal valor,
		String descricao) {}