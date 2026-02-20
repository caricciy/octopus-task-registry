package com.octopus.adapter.outbound.persistence.mongodb;

import com.octopus.adapter.outbound.persistence.mongodb.entity.TaskDefinitionEntity;
import com.octopus.adapter.outbound.persistence.mongodb.mapper.TaskDefinitionPersistenceMapper;
import com.octopus.adapter.outbound.persistence.mongodb.repository.TaskDefinitionMongoRepository;
import com.octopus.application.port.outbound.LoadTaskDefinitionPort;
import com.octopus.application.port.outbound.SaveTaskDefinitionPort;
import com.octopus.domain.entity.TaskDefinition;
import com.octopus.domain.vo.TaskDefinitionId;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class TaskDefinitionPersistenceAdapter implements LoadTaskDefinitionPort, SaveTaskDefinitionPort {

    private final TaskDefinitionMongoRepository repository;
    private final TaskDefinitionPersistenceMapper mapper;

    @Override
    public Optional<TaskDefinition> loadById(TaskDefinitionId id) {
        log.debug("Loading task definition by id: {}", id);

        return repository.findByIdOptional(id.value().toString())
                .map(mapper::toDomain);
    }

    @Override
    public Optional<TaskDefinition> loadActiveByName(String name) {
        log.debug("Loading active task definition by name: {}", name);

        return repository.findActiveByName(name)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsActiveByName(String name) {
        log.debug("Checking if active task exists by name: {}", name);

        return repository.existsActiveByName(name);
    }

    @Override
    public TaskDefinition save(TaskDefinition taskDefinition) {
        log.debug("Saving task definition: {}", taskDefinition.getTaskInfo().name());

        TaskDefinitionEntity entity = mapper.toPersistence(taskDefinition);

        repository.persist(entity);

        return mapper.toDomain(entity);
    }
}

