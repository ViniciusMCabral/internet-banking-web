package com.internet_banking.backend.dtos;

import org.hibernate.validator.constraints.br.CPF;

import com.internet_banking.backend.models.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO(Long id, @NotBlank String nome, @CPF String cpf, @Email String email) {
	
	public UsuarioDTO(Usuario usuario) {
		this(usuario.getId(), usuario.getNome(), usuario.getCpf(), usuario.getEmail());
	}

}
