package com.internet_banking.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internet_banking.backend.dtos.DadosAutenticacao;
import com.internet_banking.backend.dtos.DadosTokenJWT;
import com.internet_banking.backend.models.Usuario;
import com.internet_banking.backend.services.JWTokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

	private AuthenticationManager manager;
	private JWTokenService tokenService;

	public AutenticacaoController(AuthenticationManager manager, JWTokenService tokenService) {
		this.manager = manager;
		this.tokenService = tokenService;
	}
	
    @PostMapping
    public ResponseEntity<DadosTokenJWT> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        var authToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(authToken);       
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}