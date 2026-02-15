package com.octopus.domain.vo;

import com.octopus.domain.exception.DomainValidationException;

import java.time.Instant;

import static java.util.Objects.isNull;

public record Audit(Instant createdAt, Instant updatedAt) {

    public Audit {
        if (isNull(createdAt)) throw new DomainValidationException("createdAt cannot be null");
        if (isNull(updatedAt)) throw new DomainValidationException("updatedAt cannot be null");
        if (updatedAt.isBefore(createdAt))  throw new DomainValidationException("updatedAt cannot be before createdAt");
    }

    public static Audit createNew() {
        Instant now = Instant.now();
        return new Audit(now, now);
    }

    public Audit withUpdatedAt(Instant updatedAt) {
        return new Audit(this.createdAt, updatedAt);
    }

}
