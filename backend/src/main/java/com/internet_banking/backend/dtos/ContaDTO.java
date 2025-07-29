package com.internet_banking.backend.dtos;

import java.math.BigDecimal;

import com.internet_banking.backend.models.Conta;

public record ContaDTO(Long id, String numero, String agencia, BigDecimal saldo, String usuario) {
	
	public ContaDTO(Conta conta) {
		this(conta.getId(), conta.getNumero(), conta.getAgencia(), conta.getSaldo(), conta.getUsuario().getNome());
	}
}
