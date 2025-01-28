package org.example.portfolioapidevelopment.exception;

public class DuplicateEntityException extends RuntimeException {
    public DuplicateEntityException(String entityName, String fieldName, String value) {
        super(entityName + " already exists (" + fieldName + ": " + value + ")");
    }
}
