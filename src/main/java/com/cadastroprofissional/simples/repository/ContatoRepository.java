/**
 * Repositório para operações relacionadas a contatos no banco de dados.
 */
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

    /**
     * Encontra um contato pelo ID, garantindo que o profissional associado esteja ativo.
     * @param contatoId O ID do contato a ser encontrado.
     * @return Um Optional contendo o contato, se encontrado.
     */
    Optional<Contato> findContatoByIdAndProfissionalAtivoIsTrue(Long contatoId);

    /**
     * Realiza uma busca personalizada por contatos com base em uma string de consulta.
     * @param q A string de consulta.
     * @return Uma lista de contatos que correspondem à consulta.
     */
    @Query("SELECT c FROM Contato c WHERE " +
            "(LOWER(c.nome) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(c.contato) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "CAST(c.createdDate AS string) LIKE CONCAT('%', :q, '%') OR " +
            "LOWER(c.profissional.nome) LIKE LOWER(CONCAT('%', :q, '%')))" +
            "OR :q IS NULL")
    List<Contato> findByAnyColumn(@Param("q") String q);

    /**
     * Verifica se um contato já existe com base no número de contato.
     * @param contato O número de contato a ser verificado.
     * @return Verdadeiro se o contato existir, falso caso contrário.
     */
    Boolean existsContatoByContato(String contato);

}
