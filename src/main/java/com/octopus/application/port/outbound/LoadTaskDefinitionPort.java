package com.octopus.application.port.outbound;


import com.octopus.domain.entity.TaskDefinition;
import com.octopus.domain.vo.TaskDefinitionId;

import java.util.Optional;

public interface LoadTaskDefinitionPort {

    /**
     * Loads a task definition by its ID.
     *
     * @param id the {@link TaskDefinitionId}
     * @return optional containing the {@link TaskDefinition} if found
     */
    Optional<TaskDefinition> loadById(TaskDefinitionId id);

    /**
     * Loads an active task definition by its name.
     *
     * @param name the task name
     * @return optional containing the {@link TaskDefinition} if found and active
     */
    Optional<TaskDefinition> loadActiveByName(String name);

    /**
     * Checks if an active task with the given name exists.
     *
     * @param name the task name
     * @return true if exists, false otherwise
     */
    boolean existsActiveByName(String name);
}

