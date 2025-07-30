package com.internet_banking.backend.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.internet_banking.backend.client.EmailClient;
import com.internet_banking.backend.dtos.ContaDTO;
import com.internet_banking.backend.dtos.UsuarioForm;
import com.internet_banking.backend.models.Conta;
import com.internet_banking.backend.models.Usuario;
import com.internet_banking.backend.repositories.ContaRepository;
import com.internet_banking.backend.repositories.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ContaRepository contaRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailClient emailClient; 
    
    public UsuarioService(UsuarioRepository usuarioRepository, ContaRepository contaRepository, EmailClient emailClient) {
        this.usuarioRepository = usuarioRepository;
        this.contaRepository = contaRepository;
        this.emailClient = emailClient; 
        this.passwordEncoder = new BCryptPasswordEncoder(); 
    }

    @Transactional
    public ContaDTO cadastrar(UsuarioForm usuarioForm) {
        if (usuarioRepository.existsByEmail(usuarioForm.email())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        if (usuarioRepository.existsByCpf(usuarioForm.cpf())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(usuarioForm.nome());
        novoUsuario.setEmail(usuarioForm.email());
        novoUsuario.setCpf(usuarioForm.cpf());

        String senhaHasheada = passwordEncoder.encode(usuarioForm.senha());
        novoUsuario.setSenhaHash(senhaHasheada);

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        Conta novaConta = new Conta(String.format("%06d", usuarioSalvo.getId()), usuarioSalvo);
        contaRepository.save(novaConta);

        String body = String.format(
                "Bem-vindo ao Internet Banking! Sua conta corrente nº %s foi criada com sucesso.",
                novaConta.getNumero()
        );
        emailClient.enviar(novoUsuario.getEmail(), "Bem-vindo ao Banco Digital", body);

        return new ContaDTO(novaConta);
    }

    public ContaDTO getContaDoUsuario(String email) {
        Conta conta = contaRepository.findByUsuarioEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada para o e-mail: " + email));
        return new ContaDTO(conta);
    }
}