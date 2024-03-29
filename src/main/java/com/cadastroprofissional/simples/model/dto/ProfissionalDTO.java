/**
 * Representa um objeto de transferência de dados (DTO) para um Profissional.
 * Este DTO é usado para transferir informações sobre um profissional entre as camadas da aplicação.
 */
package com.cadastroprofissional.simples.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfissionalDTO {

    private Long id;

    private String nome;

    private Boolean ativo;

    private String cargo;

    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate dataNascimento;

    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate createdDate;

}
