/**
 * Representa os dados de entrada para criar ou atualizar um profissional.
 * Esses dados são recebidos da camada de apresentação e usados para criar ou atualizar um profissional na camada de serviço.
 */
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
