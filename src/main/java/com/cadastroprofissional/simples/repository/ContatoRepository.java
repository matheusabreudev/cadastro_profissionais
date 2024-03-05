package com.cadastroprofissional.simples.repository;

import com.cadastroprofissional.simples.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {

    Optional<Contato> findContatoByIdAndProfissionalAtivoIsTrue(Long contatoId);

    @Query("SELECT c FROM Contato c WHERE " +
            "(:q IS NULL OR " +
            " LOWER(c.nome) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            " LOWER(c.contato) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            " TO_CHAR(c.createdDate, 'YYYY-MM-DD') LIKE CONCAT('%', :q, '%') OR " +
            " LOWER(c.profissional.nome) LIKE LOWER(CONCAT('%', :q, '%')))")
    List<Contato> findByString(@Param("q") String q);

    Boolean existsContatoByContato(String contato);

}
