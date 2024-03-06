/**
 * Representa um contato de um profissional.
 * Esta classe é uma entidade JPA que mapeia os contatos dos profissionais no banco de dados.
 */
package com.cadastroprofissional.simples.model;

import com.cadastroprofissional.simples.model.dto.ContatoDTO;
import com.cadastroprofissional.simples.model.input.ContatoInput;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CONTATO")
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTATO_ID")
    private Long id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "CONTATO")
    private String contato;

    @Column(name = "CREATED_DATE")
    private LocalDate createdDate;

    @ManyToOne
    @JoinColumn(name = "PROFISSIONAL_ID")
    private Profissional profissional;

    /**
     * Converte esta entidade Contato em um objeto ContatoDTO.
     * @return Um objeto ContatoDTO contendo os dados deste contato.
     */
    public ContatoDTO toDto() {
        ContatoDTO contatoDTO = new ContatoDTO();
        contatoDTO.setId(this.id);
        contatoDTO.setNome(this.nome);
        contatoDTO.setContato(this.contato);
        contatoDTO.setCreatedDate(this.createdDate);
        if (this.profissional != null) {
            contatoDTO.setProfissional(this.profissional.getNome());
        }
        return contatoDTO;
    }

    /**
     * Construtor que cria um objeto Contato a partir dos dados de entrada.
     * @param cont Um objeto ContatoInput contendo os dados do novo contato.
     */
    public Contato (ContatoInput cont) {
        this.contato = cont.getContato();
        this.nome = cont.getNome();

        Profissional profissional = new Profissional(cont.getProfissional());
        this.profissional = profissional;
    }

}
