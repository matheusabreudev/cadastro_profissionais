/**
 * Representa um objeto de transferência de dados (DTO) para um Contato.
 * Este DTO é usado para transferir informações sobre um contato entre as camadas da aplicação.
 */
package com.cadastroprofissional.simples.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ContatoDTO {

    private Long id;

    private String nome;

    private String contato;

    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate createdDate;

    private String profissional;

}
