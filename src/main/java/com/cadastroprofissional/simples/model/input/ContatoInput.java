/**
 * Representa os dados de entrada para criar um contato.
 * Esses dados são recebidos da camada de apresentação e usados para criar um contato na camada de serviço.
 */
package com.cadastroprofissional.simples.model.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContatoInput {

    private String nome;

    private String contato;

    private Long profissional;

}
