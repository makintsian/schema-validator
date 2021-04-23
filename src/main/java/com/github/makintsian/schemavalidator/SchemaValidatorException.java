package com.github.makintsian.schemavalidator;

public class SchemaValidatorException extends RuntimeException {

    public SchemaValidatorException() {
    }

    public SchemaValidatorException(String message) {
        super(message);
    }

    public SchemaValidatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
