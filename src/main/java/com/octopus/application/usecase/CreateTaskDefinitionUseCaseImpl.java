package com.octopus.application.usecase;

import com.octopus.application.factory.TaskDefinitionFactory;
import com.octopus.application.port.inbound.CreateTaskDefinitionUseCase;
import com.octopus.application.port.inbound.command.CreateTaskDefinitionCommand;
import com.octopus.application.port.outbound.LoadTaskDefinitionPort;
import com.octopus.application.port.outbound.SaveTaskDefinitionPort;
import com.octopus.domain.entity.TaskDefinition;
import com.octopus.domain.exception.DomainBusinessRuleException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class CreateTaskDefinitionUseCaseImpl implements CreateTaskDefinitionUseCase {

    private final Validator validator;
    private final LoadTaskDefinitionPort loadTaskDefinitionPort;
    private final SaveTaskDefinitionPort saveTaskDefinitionPort;
    private final TaskDefinitionFactory taskFactory;

    @Override
    public TaskDefinition execute(CreateTaskDefinitionCommand command) {
        log.info("Creating task definition with name: {}, category: {}", command.getName(), command.getCategory());

        // Perform bean validation on the command
        validate(command);

        // Perform business validation (check for duplicate name)
        if (loadTaskDefinitionPort.existsActiveByName(command.getName())) {
            log.warn("Task definition with name '{}' already exists", command.getName());
            throw new DomainBusinessRuleException(
                    String.format("An active task definition with name '%s' already exists", command.getName()));
        }

        // Build the TaskDefinition entity from the command
        var taskDefinition = taskFactory.createFrom(command);

        // Save the new task definition
        var saved = saveTaskDefinitionPort.save(taskDefinition);

        log.info("Successfully created TaskDefinition with name '{}' and id '{}'", saved.taskInfo().name(), saved.id().value());

        return saved;
    }

    /**
     * Validates the CreateTaskDefinitionCommand using Bean Validation.
     * If any constraint violations are found, a ConstraintViolationException is thrown.
     * <p>
     * This is necessary because may our use case is called from other places than REST, so we can't rely on @Valid there.
     * </p>
     * @param command the command to validate
     * @throws ConstraintViolationException if validation fails
     */
    private void validate(CreateTaskDefinitionCommand command) {
        Set<ConstraintViolation<CreateTaskDefinitionCommand>> violations = validator.validate(command);

        if (!violations.isEmpty()) {
            log.warn("Bean validation failed for CreateTaskDefinitionCommand: {} violations found", violations.size());
            throw new ConstraintViolationException(violations);
        }
    }


}
