package com.octopus.application.port.inbound;

import com.octopus.application.port.inbound.command.CreateTaskDefinitionCommand;
import com.octopus.domain.entity.TaskDefinition;
import com.octopus.domain.exception.DomainBusinessRuleException;

public interface CreateTaskDefinitionUseCase {

    /**
     * Creates a new task definition.
     *
     * @param command the command containing task definition data
     * @return the newly created {@link TaskDefinition} entity
     * @throws DomainBusinessRuleException if a task definition with the same name already exists
     * @throws jakarta.validation.ConstraintViolationException         if the command fails validation constraints
     *                                                                 (e.g., missing required fields, invalid format, etc.)
     */
    TaskDefinition execute(CreateTaskDefinitionCommand command);

}
