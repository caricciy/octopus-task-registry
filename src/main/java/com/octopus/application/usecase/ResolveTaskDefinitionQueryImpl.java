package com.octopus.application.usecase;

import com.octopus.application.port.inbound.ResolveTaskDefinitionQuery;
import com.octopus.application.port.outbound.LoadTaskDefinitionPort;
import com.octopus.domain.exception.DomainValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class ResolveTaskDefinitionQueryImpl implements ResolveTaskDefinitionQuery {

    private final LoadTaskDefinitionPort loadTaskDefinitionPort;

    @Override
    public boolean execute(String name) {
        log.debug("Resolving task definition with name '{}'", name);

        if(Objects.isNull(name) || name.isBlank()) throw new DomainValidationException("Task definition name must not be null or blank");

        return loadTaskDefinitionPort.existsActiveByName(name);
    }
}
