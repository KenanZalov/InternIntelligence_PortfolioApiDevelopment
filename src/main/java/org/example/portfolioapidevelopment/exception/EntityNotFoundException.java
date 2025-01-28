package org.example.portfolioapidevelopment.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entityName, Long id) {
        super(entityName + " not found (ID: " + id + ")");
    }
}
