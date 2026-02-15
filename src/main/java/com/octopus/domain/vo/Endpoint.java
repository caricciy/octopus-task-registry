package com.octopus.domain.vo;

import com.octopus.domain.exception.DomainValidationException;

import java.net.URI;

import static java.util.Objects.isNull;

/**
 * Endpoint Value Object.
 * Represents an HTTP/HTTPS endpoint URL in the domain model.
 */
public record Endpoint(String url) {

    public static final int MAX_URL_LENGTH = 2048;

    public Endpoint {
        if (isNull(url)) throw new DomainValidationException("Endpoint URL cannot be null");
        if (url.isBlank()) throw new DomainValidationException("Endpoint URL cannot be blank");

        if (url.length() > MAX_URL_LENGTH) throw new DomainValidationException(
                String.format("Endpoint URL cannot exceed {%d} characters, got: %d", MAX_URL_LENGTH, url.length()));

        url = url.toLowerCase();

        validateUrl(url);

    }

    public static Endpoint of(String url) {
        return new Endpoint(url);
    }

    /**
     * Validates the URL structure and protocol.
     *
     * @param url the URL to validate
     * @throws DomainValidationException if the URL is malformed or uses invalid protocol
     */
    private void validateUrl(String url) {

        String protocol;
        String host;

        try {
            var parsedUrl = URI.create(url);
            protocol = parsedUrl.getScheme();
            host = parsedUrl.getHost();

        } catch (IllegalArgumentException e) {
            throw new DomainValidationException(String.format("Endpoint must be a valid URL: %s ", url), e);
        }

        if (isNull(protocol)) {
            throw new DomainValidationException(String.format("Endpoint URL must have a protocol (HTTP or HTTPS): %s", url));
        }

        if (!"http".equals(protocol) && !"https".equals(protocol)) {
            throw new DomainValidationException(String.format("Endpoint protocol must be HTTP or HTTPS, but was: %s", protocol));
        }

        if (isNull(host) || host.isBlank()) {
            throw new DomainValidationException(String.format("Endpoint must have a valid host: %s", url));
        }
    }

    /**
     * Checks if this endpoint uses HTTPS protocol.
     *
     * @return true if HTTPS, false otherwise
     */
    public boolean isSecure() {
        return url.startsWith("https://");
    }

}

