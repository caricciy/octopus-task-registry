package com.octopus.application.port.outbound;


import com.octopus.domain.entity.TaskDefinition;

public interface SaveTaskDefinitionPort {

    /**
     * Persists a task definition (insert or update).
     *
     * @param taskDefinition the {@link TaskDefinition} to save
     * @return the saved task (with generated ID if new)
     */
    TaskDefinition save(TaskDefinition taskDefinition);
}

