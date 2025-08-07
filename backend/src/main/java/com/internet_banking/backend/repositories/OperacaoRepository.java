package com.internet_banking.backend.repositories;

import com.internet_banking.backend.models.Operacao;
import com.internet_banking.backend.models.TipoOperacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OperacaoRepository extends JpaRepository<Operacao, Long> {

    @Query("SELECT o FROM Operacao o WHERE o.conta.numero = :numero " +
           "AND (   CAST(:de AS timestamp) IS NULL OR o.dataHora >= :de   ) " +
           "AND (   CAST(:ate AS timestamp) IS NULL OR o.dataHora <= :ate   ) " +
           "AND (   :tipo IS NULL OR o.tipo = :tipo   )")
    Page<Operacao> buscarExtrato(@Param("numero") String numero,
                                 @Param("tipo") TipoOperacao tipo,
                                 @Param("de") LocalDateTime de,
                                 @Param("ate") LocalDateTime ate,
                                 Pageable pageable);

    @Query("SELECT o FROM Operacao o WHERE o.conta.numero = :numero " +
           "AND (   CAST(:de AS timestamp) IS NULL OR o.dataHora >= :de   ) " +
           "AND (   CAST(:ate AS timestamp) IS NULL OR o.dataHora <= :ate   ) " +
           "AND (   :tipo IS NULL OR o.tipo = :tipo   )")
    List<Operacao> buscarExtrato(@Param("numero") String numero,
                                 @Param("tipo") TipoOperacao tipo,
                                 @Param("de") LocalDateTime de,
                                 @Param("ate") LocalDateTime ate);
}