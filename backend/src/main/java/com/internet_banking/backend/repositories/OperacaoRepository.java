package com.internet_banking.backend.repositories;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.internet_banking.backend.models.Operacao;
import com.internet_banking.backend.models.TipoOperacao;

public interface OperacaoRepository extends JpaRepository<Operacao, Long>{
	@Query("SELECT o FROM Operacao o WHERE o.conta.numero = :numero " +
	           "AND o.dataHora >= COALESCE(:de, o.dataHora) " +
	           "AND o.dataHora <= COALESCE(:ate, o.dataHora) " +
	           "AND o.tipo = COALESCE(:tipo, o.tipo)")
	    Page<Operacao> buscarExtrato(@Param("numero") String numero,
	                                 @Param("tipo") TipoOperacao tipo,
	                                 @Param("de") LocalDateTime de,
	                                 @Param("ate") LocalDateTime ate,
	                                 Pageable pageable);

	@Query("SELECT o FROM Operacao o WHERE o.conta.numero = :numero " +
	           "AND o.dataHora >= COALESCE(:de, o.dataHora) " +
	           "AND o.dataHora <= COALESCE(:ate, o.dataHora) " +
	           "AND o.tipo = COALESCE(:tipo, o.tipo)")
	    java.util.List<Operacao> buscarExtrato(@Param("numero") String numero,
	                                           @Param("tipo") TipoOperacao tipo,
	                                           @Param("de") LocalDateTime de,
	                                           @Param("ate") LocalDateTime ate);
}
