package com.cadastroprofissional.simples.model.dto;

import com.cadastroprofissional.simples.model.Profissional;
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
public class ContatoDTO {

    private Long id;

    private String nome;

    private String contato;

    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate createdDate;

    private String profissional;

}
