package com.octopus.domain.vo;

import com.octopus.domain.exception.DomainValidationException;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.isNull;

/**
 * Enum representing the status of a task definition.
 */
public enum TaskStatus {
    ACTIVE,
    INACTIVE,
    DEPRECATED;

    private static final Map<String, TaskStatus> ENUM_MAP;
    public static final String STATUS_PATTERN;


    static {
        Map<String, TaskStatus> map = new ConcurrentHashMap<>();
        StringBuilder sb = new StringBuilder();
        for (TaskStatus instance : TaskStatus.values()) {
            map.put(instance.name(), instance);
            sb.append(instance.name()).append("|");
        }
        sb.deleteCharAt(sb.length() - 1);
        STATUS_PATTERN = sb.toString();
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static TaskStatus fromString(String status) {
        if (isNull(status)) throw new DomainValidationException("Status cannot be null");

        TaskStatus result = ENUM_MAP.get(status.toUpperCase());
        if (isNull(result)) {
            throw new DomainValidationException(String.format("Status %s not found. Valid values are: %s", status, STATUS_PATTERN));
        }

        return result;
    }

}
