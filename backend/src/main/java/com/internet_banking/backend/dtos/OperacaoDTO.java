package com.internet_banking.backend.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.internet_banking.backend.models.Operacao;
import com.internet_banking.backend.models.TipoOperacao;

public record OperacaoDTO(Long id, TipoOperacao tipo, BigDecimal valor, String descricao, String conta, LocalDateTime dataHora) {
	
	public OperacaoDTO(Operacao operacao) {
		this(operacao.getId(), operacao.getTipo(), operacao.getValor(), operacao.getDescricao(), operacao.getConta().getNumero(), operacao.getDataHora());
	}
}
