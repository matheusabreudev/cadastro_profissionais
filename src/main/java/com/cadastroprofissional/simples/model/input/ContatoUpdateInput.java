/**
 * Representa os dados de entrada para atualizar um contato.
 * Esses dados são recebidos da camada de apresentação e usados para atualizar um contato na camada de serviço.
 */
package com.cadastroprofissional.simples.model.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContatoUpdateInput {

    private String nome;

    private String contato;
}
