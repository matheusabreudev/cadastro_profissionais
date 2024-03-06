/**
 * Exceção lançada quando um telefone é inválido.
 */
package com.cadastroprofissional.simples.util.exception;

public class TelefoneInvalidoException extends RuntimeException{

    /**
     * Construtor da exceção TelefoneInvalidoException.
     * @param message A mensagem de erro associada à exceção.
     */
    public TelefoneInvalidoException(String message) {
        super(message);
    }

}
