/**
 * Representa um profissional cadastrado no sistema.
 * Esta classe é uma entidade JPA que mapeia os profissionais no banco de dados.
 */
package com.cadastroprofissional.simples.model;

import com.cadastroprofissional.simples.model.dto.ProfissionalDTO;
import com.cadastroprofissional.simples.model.input.ProfissionalInput;
import com.cadastroprofissional.simples.util.enums.CargoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PROFISSIONAL")
public class Profissional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROFISSIONAL_ID")
    private Long id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "ATIVO")
    private boolean ativo = true;

    @Column(name = "CARGO")
    @Enumerated(EnumType.STRING)
    private CargoEnum cargo;

    @Column(name = "DATA_NASCIMENTO")
    private LocalDate dataNascimento;

    @Column(name = "CREATED_DATE")
    private LocalDate createdDate;

    @OneToMany(mappedBy = "profissional")
    private List<Contato> contatos;

    /**
     * Converte esta entidade Profissional em um objeto ProfissionalDTO.
     * @return Um objeto ProfissionalDTO contendo os dados deste profissional.
     */
    public ProfissionalDTO toDTO() {
        return ProfissionalDTO.builder().id(this.id).nome(this.nome).ativo(this.ativo).cargo(this.cargo.getDescricao()).dataNascimento(this.dataNascimento).createdDate(this.createdDate).build();
    }

    /**
     * Construtor que cria um objeto Profissional a partir de seu id com a intenção de serializar esse no momento do save via JPA.
     * @param id .
     */
    public Profissional(Long id) {
        this.id = id;
    }

    /**
     * Construtor que cria um objeto Profissional a partir dos dados de entrada.
     * @param input Um objeto ProfissionalInput contendo os dados do novo profissional.
     */
    public Profissional(ProfissionalInput input) {
        this.nome = input.getNome();
        this.cargo = CargoEnum.valueOf(input.getCargo());
        this.dataNascimento = input.getDataNascimento();
    }

}
