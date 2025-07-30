package com.internet_banking.backend.controllers;

import com.internet_banking.backend.dtos.ContaDTO;
import com.internet_banking.backend.dtos.UsuarioForm; 
import com.internet_banking.backend.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios") 
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @Operation(summary = "Cadastra um novo usuário e cria sua conta corrente")
    @ApiResponse(responseCode = "201", description = "Usuário e conta criados com sucesso")
    public ResponseEntity<ContaDTO> cadastrar(@RequestBody @Valid UsuarioForm dto) {
        ContaDTO contaCriadaDTO = usuarioService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(contaCriadaDTO);
    }
}