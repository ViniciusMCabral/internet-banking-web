package com.internet_banking.backend.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.internet_banking.backend.dtos.OperacaoDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Operacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoOperacao tipo;

    private BigDecimal valor;

    private LocalDateTime dataHora = LocalDateTime.now();

    private String descricao;

    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Conta conta;

	public Operacao(Long id, TipoOperacao tipo, BigDecimal valor, String descricao, Conta conta) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.valor = valor;
		this.descricao = descricao;
		this.conta = conta;
	}
	
	public Operacao(OperacaoDTO operacaoDTO) {
		this.id = operacaoDTO.id();
		this.tipo = operacaoDTO.tipo();
		this.valor = operacaoDTO.valor();
		this.descricao = operacaoDTO.descricao();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoOperacao getTipo() {
		return tipo;
	}

	public void setTipo(TipoOperacao tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}
    
    
}
