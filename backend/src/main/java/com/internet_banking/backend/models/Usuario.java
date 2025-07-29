package com.internet_banking.backend.models;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.br.CPF;

import com.internet_banking.backend.dtos.UsuarioForm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String nome;
	
	@CPF
	@Column(unique = true, nullable = false)
	private String cpf;
	
	@Email
	@Column(unique = true, nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String senhaHash;
	
	private LocalDateTime dataCadastro = LocalDateTime.now();
		
	public Usuario(Long id, @NotBlank String nome, @CPF String cpf, @Email String email, String senhaHash) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.senhaHash = senhaHash;
	}
	
	public Usuario(UsuarioForm usuarioForm) {
		this.id = usuarioForm.id();
		this.nome = usuarioForm.nome();
		this.cpf = usuarioForm.cpf();
		this.email = usuarioForm.email();
		this.senhaHash = usuarioForm.senhaHash();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenhaHash() {
		return senhaHash;
	}

	public void setSenhaHash(String senhaHash) {
		this.senhaHash = senhaHash;
	}

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	
}
