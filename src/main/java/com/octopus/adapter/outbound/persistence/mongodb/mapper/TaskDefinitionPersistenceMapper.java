package com.octopus.adapter.outbound.persistence.mongodb.mapper;

import com.octopus.adapter.outbound.persistence.mongodb.entity.TaskDefinitionEntity;
import com.octopus.domain.entity.TaskDefinition;
import com.octopus.domain.vo.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Arrays;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
        componentModel = "jakarta-cdi",
        nullValuePropertyMappingStrategy = IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TaskDefinitionPersistenceMapper {

    // TaskDefinition to TaskDefinitionEntity
    @Mapping(target = "id", expression = "java(mapDomainIdToString(domain.id()))")
    @Mapping(target = "name", expression = "java(domain.taskInfo().name())")
    @Mapping(target = "category", expression = "java(domain.taskInfo().category())")
    @Mapping(target = "description", expression = "java(domain.taskInfo().description())")
    @Mapping(target = "status", expression = "java(domain.taskStatus().name())")
    @Mapping(target = "metadata", expression = "java(domain.metadata())")
    @Mapping(target = "httpConfig", expression = "java(toHttpConfigEntity(domain.httpConfig()))")
    @Mapping(target = "retryPolicy", expression = "java(toRetryPolicyEntity(domain.retryPolicy()))")
    @Mapping(target = "createdAt", expression = "java(domain.audit().createdAt())")
    @Mapping(target = "updatedAt", expression = "java(domain.audit().updatedAt())")
    TaskDefinitionEntity toPersistence(TaskDefinition domain);

    // TaskDefinitionEntity to TaskDefinition
    @Mapping(target = "id", expression = "java(mapStringToDomainId(entity.getId()))")
    @Mapping(target = "taskInfo", expression = "java(mapToTaskInfo(entity))")
    @Mapping(target = "taskStatus", expression = "java(mapToTaskStatus(entity.getStatus()))")
    @Mapping(target = "metadata", source = "metadata")
    @Mapping(target = "httpConfig", expression = "java(toHttpConfig(entity.getHttpConfig()))")
    @Mapping(target = "retryPolicy", expression = "java(toRetryPolicy(entity.getRetryPolicy()))")
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

    // RetryPolicy to RetryPolicyEntity
    default TaskDefinitionEntity.RetryPolicyEntity toRetryPolicyEntity(RetryPolicy retryPolicy) {
        if (retryPolicy == null) {
            return null;
        }
        return TaskDefinitionEntity.RetryPolicyEntity.builder()
                .maxAttempts(retryPolicy.maxAttempts())
                .backoffSeconds(retryPolicy.backoffSeconds() != null ? Arrays.asList(retryPolicy.backoffSeconds()) : null)
                .retryableStatusCodes(retryPolicy.retryableStatusCodes() != null ? Arrays.asList(retryPolicy.retryableStatusCodes()) : null)
                .build();
    }

    // RetryPolicyEntity to RetryPolicy
    default RetryPolicy toRetryPolicy(TaskDefinitionEntity.RetryPolicyEntity entity) {
        if (entity == null) {
            return null;
        }
        return RetryPolicy.builder()
                .maxAttempts(entity.getMaxAttempts())
                .backoffSeconds(entity.getBackoffSeconds() != null ? entity.getBackoffSeconds().toArray(new Integer[0]) : null)
                .retryableStatusCodes(entity.getRetryableStatusCodes() != null ? entity.getRetryableStatusCodes().toArray(new Integer[0]) : null)
                .build();
    }

    // HttpConfigEntity to HttpConfig
    default HttpConfig toHttpConfig(TaskDefinitionEntity.HttpConfigEntity entity) {
        if (entity == null) {
            return null;
        }
        return HttpConfig.builder()
                .endpoint(Endpoint.of(entity.getEndpoint()))
                .httpMethod(HttpMethod.fromString(entity.getHttpMethod()))
                .timeoutSeconds(entity.getTimeoutSeconds())
                .headers(entity.getHeaders())
                .payloadTemplate(entity.getPayloadTemplate())
                .build();
    }

    // String to TaskStatus
    default TaskStatus mapToTaskStatus(String status) {
        return status != null ? TaskStatus.fromString(status) : null;
    }

}
