package com.octopus.domain.vo;

import com.octopus.domain.exception.DomainValidationException;
import lombok.Builder;

import java.util.Map;
import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * HttpConfig Value Object.
 * Represents HTTP configuration in the domain model.
 */
@Builder
public record HttpConfig(Endpoint endpoint,
                         HttpMethod httpMethod,
                         Integer timeoutSeconds,
                         Map<String, String> headers,
                         Map<String, Object> payloadTemplate) {

    public static final int MIN_TIMEOUT_SECONDS = 1;
    public static final int MAX_TIMEOUT_SECONDS = 300; // 5 minutes
    public static final String HEADER_KEY_PATTERN = "^[a-zA-Z0-9-]+$";

    public HttpConfig {
        if (isNull(endpoint)) throw new DomainValidationException("Endpoint cannot be null");
        if (isNull(httpMethod)) throw new DomainValidationException("HTTP method cannot be null");
        if (isNull(timeoutSeconds)) throw new DomainValidationException("Timeout seconds cannot be null");

        validateTimeout(timeoutSeconds, httpMethod);
        validateHeaders(headers);
    }

    private void validateTimeout(Integer timeoutSeconds, HttpMethod httpMethod) {
        if (timeoutSeconds < MIN_TIMEOUT_SECONDS) throw new DomainValidationException(
                String.format("Timeout must be at least %d second, got: %d", MIN_TIMEOUT_SECONDS, timeoutSeconds));

        if (httpMethod == HttpMethod.GET && timeoutSeconds > MAX_TIMEOUT_SECONDS) throw new DomainValidationException(
                String.format("GET requests should not have timeout exceeding %d seconds", MAX_TIMEOUT_SECONDS));
    }

    private void validateHeaders(Map<String, String> headers) {
        if (nonNull(headers)) {
            headers.forEach((key, value) -> {
                if (isNull(key) || key.isBlank())throw new DomainValidationException("Header key cannot be null or blank");
                if (isNull(value)) throw new DomainValidationException(String.format("Header value for key '%s' cannot be null", key));
                if (!key.matches(HEADER_KEY_PATTERN))throw new DomainValidationException(String.format("Header value for key '%s' contains invalid characters", key));

            });
        }
    }

    public boolean hasPayloadTemplate() {
        return nonNull(payloadTemplate) && !payloadTemplate.isEmpty();
    }

    public boolean hasHeaders() {
        return nonNull(headers) && !headers.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HttpConfig(
                Endpoint endpoint1, HttpMethod method, Integer seconds, Map<String, String> headers1,
                Map<String, Object> template
        ))) return false;
        return Objects.equals(endpoint, endpoint1)
                && httpMethod == method
                && Objects.equals(timeoutSeconds, seconds)
                && Objects.equals(headers, headers1)
                && Objects.equals(payloadTemplate, template);
    }

    @Override
    public int hashCode() {
        return Objects.hash(endpoint, httpMethod, timeoutSeconds, headers, payloadTemplate);
    }
}

