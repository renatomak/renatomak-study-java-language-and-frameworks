package br.com.handson.store.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String mensage) {
        super(mensage);
    }
}
