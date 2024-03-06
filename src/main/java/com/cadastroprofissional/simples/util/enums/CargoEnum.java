/**
 * Enumeração que representa os diferentes cargos de um profissional.
 */
package com.cadastroprofissional.simples.util.enums;

public enum CargoEnum {

    DESENVOLVEDOR("Desenvolvedor"),
    DESIGNER("Designer"),
    SUPORTE("Suporte"),
    TESTER("Tester");

    private final String descricao;

    /**
     * Construtor privado para o enum CargoEnum.
     * @param descricao A descrição do cargo.
     */
    CargoEnum(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Obtém a descrição do cargo.
     * @return A descrição do cargo.
     */
    public String getDescricao() {
        return descricao;
    }

}
