package com.octopus.domain.vo;

import com.octopus.domain.exception.DomainValidationException;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.isNull;

public enum HttpMethod {
    GET,
    POST,
    PUT,
    DELETE;

    private static final Map<String, HttpMethod> ENUM_MAP;
    private static final String HTTP_METHOD_PATTERN;

    static {
        Map<String, HttpMethod> map = new ConcurrentHashMap<>();
        StringBuilder sb = new StringBuilder();
        for (HttpMethod instance : HttpMethod.values()) {
            map.put(instance.name(), instance);
            sb.append(instance.name()).append("|");
        }
        sb.deleteCharAt(sb.length() - 1);
        HTTP_METHOD_PATTERN = sb.toString();
        ENUM_MAP = Collections.unmodifiableMap(map);

    }

    public static HttpMethod fromString(String httpMethod) {
        if (isNull(httpMethod)) throw new DomainValidationException("HTTP method cannot be null");

        HttpMethod result = ENUM_MAP.get(httpMethod.toUpperCase());
        if (isNull(result)) {
            throw new DomainValidationException(String.format("HTTP method %s not found. Valid values are: %s", httpMethod, HTTP_METHOD_PATTERN));
        }

        return result;
    }
}

