package com.octopus.domain.vo;

import com.octopus.domain.exception.DomainValidationException;
import lombok.Builder;

import java.util.Arrays;

import static java.util.Objects.isNull;

/**
 * RetryPolicy Value Object.
 * Represents retry configuration in the domain model.
 */
@Builder
public record RetryPolicy(Integer maxAttempts,
                          Integer[] backoffSeconds,
                          Integer[] retryableStatusCodes) {

    public static final int MIN_HTTP_STATUS_CODE = 100;
    public static final int MAX_HTTP_STATUS_CODE = 599;
    public static final int MIN_ATTEMPTS_LIMIT = 0;
    public static final int MAX_ATTEMPTS_LIMIT = 20;

    public RetryPolicy {
        if (isNull(maxAttempts)) throw new DomainValidationException("maxAttempts cannot be null");
        if (maxAttempts < MIN_ATTEMPTS_LIMIT) throw new DomainValidationException(String.format("maxAttempts cannot be less than %d", MIN_ATTEMPTS_LIMIT));
        if (maxAttempts > MAX_ATTEMPTS_LIMIT) throw new DomainValidationException(String.format("maxAttempts cannot exceed %d", MAX_ATTEMPTS_LIMIT));


        if (maxAttempts > 0) {
            validateBackoffSeconds(backoffSeconds, maxAttempts);
            validateRetryableStatusCodes(retryableStatusCodes);
        }
    }

    private void validateBackoffSeconds(Integer[] backoffSeconds, Integer maxAttempts) {

        if (isNull(backoffSeconds)) {
            throw new DomainValidationException(String.format("backoffSeconds cannot be null when maxAttempts is greater than %d. Provided maxAttempts: %d", MIN_ATTEMPTS_LIMIT, maxAttempts));
        }

        if (backoffSeconds.length == 0) {
            throw new DomainValidationException("backoffSeconds must have at least one element when maxAttempts is greater than 0");
        }

        if (backoffSeconds.length < maxAttempts) {
            throw new DomainValidationException(String.format("backoffSeconds array length (%d) cannot be less than maxAttempts (%d)", backoffSeconds.length, maxAttempts));
        }

        for (int i = 0; i < backoffSeconds.length; i++) {
            if (backoffSeconds[i] < 0) {
                throw new DomainValidationException(String.format("backoff Seconds at index %d cannot be negative: %d", i, backoffSeconds[i]));
            }
        }
    }

    private void validateRetryableStatusCodes(Integer[] retryableStatusCodes) {
        if (isNull(retryableStatusCodes)) throw new DomainValidationException(String.format("retryableStatusCodes cannot be null when maxAttempts is greater than %d", MIN_ATTEMPTS_LIMIT));
        if (retryableStatusCodes.length == 0) {
            throw new DomainValidationException(String.format("retryableStatusCodes must have at least one element when maxAttempts is greater than %d", MIN_ATTEMPTS_LIMIT));
        }

        for (int i = 0; i < retryableStatusCodes.length; i++) {
            if (retryableStatusCodes[i] < MIN_HTTP_STATUS_CODE || retryableStatusCodes[i] > MAX_HTTP_STATUS_CODE) {
                throw new DomainValidationException(String.format("Invalid HTTP status code at index %d: %d. Valid range is %d-%d", i, retryableStatusCodes[i], MIN_HTTP_STATUS_CODE, MAX_HTTP_STATUS_CODE));
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RetryPolicy(Integer attempts, Integer[] seconds, Integer[] statusCodes))) return false;

        if (!maxAttempts.equals(attempts)) return false;
        if (!java.util.Arrays.equals(backoffSeconds, seconds)) return false;
        return java.util.Arrays.equals(retryableStatusCodes, statusCodes);
    }

    @Override
    public int hashCode() {
        int result = maxAttempts.hashCode();
        result = 31 * result + java.util.Arrays.hashCode(backoffSeconds);
        result = 31 * result + java.util.Arrays.hashCode(retryableStatusCodes);
        return result;
    }

    @Override
    public String toString() {
        return String.format("RetryPolicy{maxAttempts=%d, backoffSeconds=%s, retryableStatusCodes=%s}",
                maxAttempts, Arrays.toString(backoffSeconds), Arrays.toString(retryableStatusCodes));
    }

}

