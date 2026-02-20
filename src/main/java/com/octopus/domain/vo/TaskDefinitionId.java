package com.octopus.domain.vo;

import com.octopus.domain.exception.DomainValidationException;

import java.util.UUID;

import static java.util.Objects.isNull;


public record TaskDefinitionId(UUID value) {

    public TaskDefinitionId {
        if(isNull(value)) throw new DomainValidationException("uuid value cannot be null");
    }

    public static TaskDefinitionId random() {
        return new TaskDefinitionId(UUID.randomUUID());
    }

    public static TaskDefinitionId of(String value) {
        if (isNull(value)) throw new DomainValidationException("id string value cannot be null");

        try {
            var uuid = UUID.fromString(value);
            return new TaskDefinitionId(uuid);
        } catch (IllegalArgumentException e) {
            throw new DomainValidationException("id string value must be a valid UUID", e);
        }
    }

    public static TaskDefinitionId of(UUID value) {
        return new TaskDefinitionId(value);
    }
}

