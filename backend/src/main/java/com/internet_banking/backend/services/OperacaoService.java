package com.internet_banking.backend.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.internet_banking.backend.client.EmailClient;
import com.internet_banking.backend.dtos.ContaDTO;
import com.internet_banking.backend.models.Conta;
import com.internet_banking.backend.models.Operacao;
import com.internet_banking.backend.models.TipoOperacao;
import com.internet_banking.backend.repositories.ContaRepository;
import com.internet_banking.backend.repositories.OperacaoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class OperacaoService {

    private static final Logger logger = LoggerFactory.getLogger(OperacaoService.class);
    private final ContaRepository contaRepository;
    private final OperacaoRepository operacaoRepository;
    private final EmailClient emailClient;

    public OperacaoService(ContaRepository contaRepository, OperacaoRepository operacaoRepository, EmailClient emailClient) {
        this.contaRepository = contaRepository;
        this.operacaoRepository = operacaoRepository;
        this.emailClient = emailClient;
    }

    @Transactional
    public ContaDTO depositar(String numero, BigDecimal valor) { 
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser positivo.");
        }
        Conta conta = contaRepository.findByNumero(numero)
                .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada: " + numero));

        conta.setSaldo(conta.getSaldo().add(valor));
        contaRepository.save(conta);

        registrarOperacao(conta, TipoOperacao.DEPOSITO, valor, "Depósito em conta");
        logger.info("Depósito realizado na conta {} no valor de {}", numero, valor);

        String body = String.format("Depósito de R$ %s. Saldo atual: R$ %s", valor, conta.getSaldo());
        emailClient.enviar(conta.getUsuario().getEmail(), "Operação Realizada", body);

        return new ContaDTO(conta); 
    }

    @Transactional
    public ContaDTO sacar(String numero, BigDecimal valor) { 
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser positivo.");
        }
        Conta conta = contaRepository.findByNumero(numero)
                .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada: " + numero));

        return debitarDaConta(conta, valor, TipoOperacao.SAQUE, "Saque de conta");
    }

    @Transactional
    public ContaDTO pagamento(String numero, BigDecimal valor, String descricao) { 
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do pagamento deve ser positivo.");
        }
        Conta conta = contaRepository.findByNumero(numero)
                .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada: " + numero));

        return debitarDaConta(conta, valor, TipoOperacao.PAGAMENTO, descricao);
    }

    private ContaDTO debitarDaConta(Conta conta, BigDecimal valor, TipoOperacao tipo, String descricao) { 
        if (conta.getSaldo().compareTo(valor) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para a operação.");
        }
        conta.setSaldo(conta.getSaldo().subtract(valor));
        contaRepository.save(conta);

        registrarOperacao(conta, tipo, valor, descricao);
        logger.info("{} realizado na conta {} no valor de {}", tipo.toString(), conta.getNumero(), valor);

        String body = String.format("Operação de %s no valor de R$ %s realizada. Saldo atual: R$ %s", tipo.toString(), valor, conta.getSaldo());
        emailClient.enviar(conta.getUsuario().getEmail(), "Operação Realizada", body);

        return new ContaDTO(conta); 
    }

    private void registrarOperacao(Conta conta, TipoOperacao tipo, BigDecimal valor, String descricao) {
        Operacao op = new Operacao(tipo, valor, descricao, conta);
        operacaoRepository.save(op);
    }

    public Page<Operacao> extrato(String numero, TipoOperacao tipo, LocalDateTime de, LocalDateTime ate, Pageable pageable) {
        return operacaoRepository.buscarExtrato(numero, tipo, de, ate, pageable);
    }

    public Conta getContaPorEmail(String email) {
        return contaRepository.findByUsuarioEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada para o e-mail: " + email));
    }
}