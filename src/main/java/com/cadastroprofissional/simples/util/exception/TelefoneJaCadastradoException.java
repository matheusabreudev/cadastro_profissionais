/**
 * Exceção lançada quando um telefone já está cadastrado na base de dados.
 */
package com.cadastroprofissional.simples.util.exception;

public class TelefoneJaCadastradoException extends RuntimeException{

    /**
     * Construtor da exceção TelefoneJaCadastradoException.
     * @param message A mensagem de erro associada à exceção.
     */
    public TelefoneJaCadastradoException(String message) {
        super(message);
    }
}
