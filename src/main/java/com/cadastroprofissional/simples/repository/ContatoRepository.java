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
            " lower(c.nome) LIKE lower(CONCAT('%', :q, '%')) OR " +
            " lower(c.contato) LIKE lower(CONCAT('%', :q, '%')) OR " +
            " TO_CHAR(c.createdDate, 'YYYY-MM-DD') LIKE CONCAT('%', :q, '%') OR " +
            " lower(c.profissional.nome) LIKE lower(CONCAT('%', :q, '%')))")
    List<Contato> findByString(@Param("q") String q);

    Boolean existsContatoByContato(String contato);

}
