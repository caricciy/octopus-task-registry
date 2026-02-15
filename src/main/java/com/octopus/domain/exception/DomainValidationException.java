package com.octopus.domain.exception;

/**
 * Exception thrown when a domain validation error occurs.
 * This is a domain exception representing a business rule violation.
 */
public class DomainValidationException extends RuntimeException {
    public DomainValidationException(String message) {
        super(message);
    }

    public DomainValidationException(String message, Exception e) {
        super(message, e);
    }
}
