package com.octopus.adapter.outbound.persistence.mongodb.repository;

import com.octopus.adapter.outbound.persistence.mongodb.entity.TaskDefinitionEntity;
import com.octopus.domain.vo.TaskStatus;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class TaskDefinitionMongoRepository implements PanacheMongoRepository<TaskDefinitionEntity> {

    /**
     * Find a task by name where status is ACTIVE.
     */
    public Optional<TaskDefinitionEntity> findActiveByName(String name) {
        return find("name = ?1 and status = ?2", name, TaskStatus.ACTIVE).firstResultOptional();
    }

    /**
     * Check if an active task with the given name exists.
     */
    public boolean existsActiveByName(String name) {
        return count("name = ?1 and status = ?2", name, TaskStatus.ACTIVE) > 0;
    }
}

