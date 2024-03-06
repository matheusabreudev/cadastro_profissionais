/**
 * Exceção lançada quando uma entidade não é encontrada.
 */
package com.cadastroprofissional.simples.util.exception;

public class EntidadeNaoExistenteException extends RuntimeException {

    /**
     * Construtor da exceção EntidadeNaoExistenteException.
     * @param message A mensagem de erro associada à exceção.
     */
    public EntidadeNaoExistenteException(String message) {
        super(message);
    }
}
