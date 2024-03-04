package com.cadastroprofissional.simples.repository;

import com.cadastroprofissional.simples.model.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {

    Optional<Profissional> findProfissionalByIdAndAtivoIsTrue(Long profissionalId);

    @Query("SELECT p FROM Profissional p WHERE " +
            "(:q IS NULL OR " +
            " LOWER(p.nome) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            " LOWER(p.cargo) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            " TO_CHAR(p.dataNascimento, 'YYYY-MM-DD') LIKE CONCAT('%', :q, '%') OR " +
            " TO_CHAR(p.createdDate, 'YYYY-MM-DD') LIKE CONCAT('%', :q, '%')) AND " +
            " p.ativo = true")
    List<Profissional> findByString( String q);

}
