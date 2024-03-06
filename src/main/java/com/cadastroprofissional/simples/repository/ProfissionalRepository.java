package com.cadastroprofissional.simples.repository;

import com.cadastroprofissional.simples.model.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {

    /**
     * Busca um profissional pelo ID e verifica se está ativo.
     * @param profissionalId O ID do profissional a ser buscado.
     * @return Um Optional contendo o profissional, se encontrado.
     */
    Optional<Profissional> findProfissionalByIdAndAtivoIsTrue(Long profissionalId);

    /**
     * Busca profissionais com base em uma string de consulta.
     * @param q A string de consulta.
     * @return Uma lista de profissionais que correspondem à consulta.
     */
    @Query("SELECT p FROM Profissional p WHERE " +
            "(LOWER(p.nome) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(p.cargo) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "CAST(p.dataNascimento AS string) LIKE CONCAT('%', :q, '%') OR " +
            "CAST(p.createdDate AS string) LIKE CONCAT('%', :q, '%'))" +
            "OR :q IS NULL AND p.ativo = true")
    List<Profissional> findByAnyColumn(@Param("q") String q);

}
