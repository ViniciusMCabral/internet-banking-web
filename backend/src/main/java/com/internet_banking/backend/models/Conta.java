package com.internet_banking.backend.models;

import jakarta.persistence.*;
import java.math.BigDecimal;

import com.internet_banking.backend.dtos.ContaDTO;

@Entity
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String numero;

    private String agencia = "0001";

    private BigDecimal saldo = BigDecimal.ZERO;

    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true)
    private Usuario usuario;
    
	public Conta(Long id, String numero, String agencia, BigDecimal saldo, Usuario usuario) {
		super();
		this.id = id;
		this.numero = numero;
		this.agencia = agencia;
		this.saldo = saldo;
		this.usuario = usuario;
	}
	
	public Conta(ContaDTO contaDTO) {
		this.id = contaDTO.id();
		this.numero = contaDTO.numero();
		this.agencia = contaDTO.agencia();
		this.saldo = contaDTO.saldo();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
    
    
}