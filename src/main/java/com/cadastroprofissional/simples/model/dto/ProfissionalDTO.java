package com.cadastroprofissional.simples.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
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
