package com.cadastroprofissional.simples.model.input;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProfissionalInput {

    private String nome;

    private String cargo;

    private LocalDate dataNascimento;

}
