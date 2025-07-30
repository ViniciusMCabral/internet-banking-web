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
	@Query("select o from Operacao o where o.conta.numero = :numero " +
            "and (:de is null or o.dataHora >= :de) " +
            "and (:ate is null or o.dataHora <= :ate) " +
            "and (:tipo is null or o.tipo = :tipo)")
    Page<Operacao> buscarExtrato(@Param("numero") String numero,
                                 @Param("tipo") TipoOperacao tipo,
                                 @Param("de") LocalDateTime de,
                                 @Param("ate") LocalDateTime ate,
                                 Pageable pageable);

    @Query("select o from Operacao o where o.conta.numero = :numero " +
            "and (:de is null or o.dataHora >= :de) " +
            "and (:ate is null or o.dataHora <= :ate) " +
            "and (:tipo is null or o.tipo = :tipo)")
    java.util.List<Operacao> buscarExtrato(@Param("numero") String numero,
                                           @Param("tipo") TipoOperacao tipo,
                                           @Param("de") LocalDateTime de,
                                           @Param("ate") LocalDateTime ate);
}
