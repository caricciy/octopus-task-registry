package com.octopus.domain.exception;

/**
 * Exception thrown when a domain business rule violation occurs.
 * This is a domain exception representing a business rule violation.
 */
public class DomainBusinessRuleException extends RuntimeException {
    public DomainBusinessRuleException(String message) {
        super(message);
    }
}
