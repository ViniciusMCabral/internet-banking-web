package com.internet_banking.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.internet_banking.backend.models.Conta;

import jakarta.persistence.LockModeType;

public interface ContaRepository extends JpaRepository<Conta, Long>{
	@Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Conta> findByNumero(String numero);
    Optional<Conta> findByUsuarioEmail(String email);
}
