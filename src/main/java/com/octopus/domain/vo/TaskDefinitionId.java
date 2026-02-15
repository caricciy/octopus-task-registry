package com.octopus.domain.vo;

import com.octopus.domain.exception.DomainValidationException;

import java.util.Objects;

import static java.util.Objects.isNull;


public record TaskDefinitionId(String value) {

    public TaskDefinitionId {
        if(isNull(value)) throw new DomainValidationException("value cannot be null");
        if (value.isBlank()) throw new DomainValidationException("Task ID cannot be null or blank");
    }

    public static TaskDefinitionId of(String value) {
        return new TaskDefinitionId(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskDefinitionId(String thatValue))) return false;
        return Objects.equals(value, thatValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

