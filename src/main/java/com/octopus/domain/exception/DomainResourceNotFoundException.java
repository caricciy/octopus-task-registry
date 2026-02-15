package com.octopus.domain.exception;

/**
 * Exception thrown when a domain resource is not found.
 * This is a domain exception representing a resource not found a scenario.
 * It can be used to indicate that a requested resource does not exist in the domain.
 */
public class DomainResourceNotFoundException extends RuntimeException {
    public DomainResourceNotFoundException(String message) {
        super(message);
    }

    public DomainResourceNotFoundException(String message, Exception e) {
        super(message, e);
    }
}
