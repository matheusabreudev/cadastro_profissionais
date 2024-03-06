package com.cadastroprofissional.simples.util.exception;

public class CargoInvalidoException extends RuntimeException {
    /**
     * Construtor da exceção CargoInvalidoException.
     * @param message A mensagem de erro associada à exceção.
     */
    public CargoInvalidoException(String message) {
        super(message);
    }
}
