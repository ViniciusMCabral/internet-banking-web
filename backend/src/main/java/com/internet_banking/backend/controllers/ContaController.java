package com.internet_banking.backend.controllers; 

import com.internet_banking.backend.dtos.ContaDTO;
import com.internet_banking.backend.dtos.OperacaoDTO;
import com.internet_banking.backend.dtos.OperacaoRequestDTO; 
import com.internet_banking.backend.models.Operacao;
import com.internet_banking.backend.models.TipoOperacao;
import com.internet_banking.backend.services.OperacaoService;
import com.internet_banking.backend.services.UsuarioService; 
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/contas")
public class ContaController {

    private final OperacaoService operacaoService;
    private final UsuarioService usuarioService; 

    public ContaController(OperacaoService operacaoService, UsuarioService usuarioService) {
        this.operacaoService = operacaoService;
        this.usuarioService = usuarioService; 
    }

    @GetMapping("/minha-conta")
    @Operation(summary = "Busca os dados da conta do usuário autenticado")
    public ResponseEntity<ContaDTO> getMinhaConta(Authentication authentication) {
        String emailUsuarioLogado = authentication.getName();
        ContaDTO contaDTO = usuarioService.getContaDoUsuario(emailUsuarioLogado);
        return ResponseEntity.ok(contaDTO);
    }

    @PostMapping("/{numeroConta}/depositos")
    @Operation(summary = "Realiza um depósito em uma conta")
    public ResponseEntity<ContaDTO> depositar(@PathVariable String numeroConta, @Valid @RequestBody OperacaoRequestDTO dto, Authentication authentication) {
        verificarDonoDaConta(authentication, numeroConta);
    	ContaDTO contaAtualizada = operacaoService.depositar(numeroConta, dto.valor());
        return ResponseEntity.ok(contaAtualizada);
    }

    @PostMapping("/{numeroConta}/saques")
    @Operation(summary = "Realiza um saque da conta do usuário autenticado")
    public ResponseEntity<ContaDTO> sacar(@PathVariable String numeroConta,
                                          @Valid @RequestBody OperacaoRequestDTO dto,
                                          Authentication authentication) {
        verificarDonoDaConta(authentication, numeroConta);
        ContaDTO contaAtualizada = operacaoService.sacar(numeroConta, dto.valor());
        return ResponseEntity.ok(contaAtualizada);
    }

    @PostMapping("/{numeroConta}/pagamentos")
    @Operation(summary = "Realiza um pagamento da conta do usuário autenticado")
    public ResponseEntity<ContaDTO> pagar(@PathVariable String numeroConta,
                                          @Valid @RequestBody OperacaoRequestDTO dto,
                                          Authentication authentication) {
        verificarDonoDaConta(authentication, numeroConta);
        ContaDTO contaAtualizada = operacaoService.pagamento(numeroConta, dto.valor(), dto.descricao());
        return ResponseEntity.ok(contaAtualizada);
    }

    @GetMapping("/{numeroConta}/extrato")
    @Operation(summary = "Gera o extrato de uma conta")
    public ResponseEntity<Page<OperacaoDTO>> extrato(@PathVariable String numeroConta,
                                                     @RequestParam(required = false) TipoOperacao tipo,
                                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
                                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
                                                     Pageable pageable,
                                                     Authentication authentication) {
        verificarDonoDaConta(authentication, numeroConta);
        
        if (dataInicio != null && dataFim != null && dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("Data inicial não pode ser posterior à data final");
        }
        
        Page<Operacao> operacoes = operacaoService.extrato(numeroConta, tipo, dataInicio, dataFim, pageable);
        Page<OperacaoDTO> dtoPage = operacoes.map(OperacaoDTO::new);
        return ResponseEntity.ok(dtoPage);
    }

    private void verificarDonoDaConta(Authentication authentication, String numeroConta) {
        String emailUsuarioLogado = authentication.getName();

        ContaDTO contaDoUsuario = usuarioService.getContaDoUsuario(emailUsuarioLogado);
        
        if (contaDoUsuario == null) {
            throw new AccessDeniedException("Conta do usuário não encontrada.");
        }
        
        if (!contaDoUsuario.numero().equals(numeroConta)) {
            throw new AccessDeniedException("Acesso negado. Você não tem permissão para operar nesta conta.");
        }
    }

}