package com.octopus.adapter.outbound.persistence.mongodb.mapper;

import com.octopus.adapter.outbound.persistence.mongodb.entity.TaskDefinitionEntity;
import com.octopus.domain.entity.TaskDefinition;
import com.octopus.domain.vo.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
        componentModel = "jakarta-cdi",
        nullValuePropertyMappingStrategy = IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TaskDefinitionPersistenceMapper {

    // TaskDefinition to TaskDefinitionEntity
    @Mapping(target = "id", expression = "java(mapDomainIdToString(domain.getId()))")
    @Mapping(target = "name", source = "taskInfo.name")
    @Mapping(target = "category", source = "taskInfo.category")
    @Mapping(target = "description", source = "taskInfo.description")
    @Mapping(target = "status", source = "taskStatus")
    @Mapping(target = "httpConfig", source = "httpConfig")
    @Mapping(target = "createdAt", source = "audit.createdAt")
    @Mapping(target = "updatedAt", source = "audit.updatedAt")
    TaskDefinitionEntity toPersistence(TaskDefinition domain);

    // TaskDefinitionEntity to TaskDefinition
    @Mapping(target = "id", expression = "java(mapStringToDomainId(entity.getId()))")
    @Mapping(target = "taskInfo", expression = "java(mapToTaskInfo(entity))")
    @Mapping(target = "taskStatus", source = "status")
    @Mapping(target = "httpConfig", source = "httpConfig")
    @Mapping(target = "audit", expression = "java(mapToAudit(entity))")
    TaskDefinition toDomain(TaskDefinitionEntity entity);

    // HttpConfig to HttpConfigEntity
    @Mapping(target = "endpoint", source = "endpoint.url")
    @Mapping(target = "httpMethod", source = "httpMethod")
    @Mapping(target = "timeoutSeconds", source = "timeoutSeconds")
    @Mapping(target = "headers", source = "headers")
    @Mapping(target = "payloadTemplate", source = "payloadTemplate")
    TaskDefinitionEntity.HttpConfigEntity toHttpConfigEntity(HttpConfig httpConfig);

    default String mapDomainIdToString(TaskDefinitionId id) {
        return id != null ? id.value().toString() : TaskDefinitionId.random().value().toString();
    }

    default TaskDefinitionId mapStringToDomainId(String id) {
        return id != null ? TaskDefinitionId.of(id) : null;
    }

    default TaskInfo mapToTaskInfo(TaskDefinitionEntity entity) {
        if (entity == null) {
            return null;
        }
        return TaskInfo.builder()
                .name(entity.getName())
                .category(entity.getCategory())
                .description(entity.getDescription())
                .build();
    }

    default Audit mapToAudit(TaskDefinitionEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Audit(entity.getCreatedAt(), entity.getUpdatedAt());
    }

    default Endpoint mapToEndpoint(String url) {
        return url != null ? Endpoint.of(url) : null;
    }

}
