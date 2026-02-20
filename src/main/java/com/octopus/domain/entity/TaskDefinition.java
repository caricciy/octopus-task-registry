package com.octopus.domain.entity;

import com.octopus.domain.exception.DomainValidationException;
import com.octopus.domain.vo.*;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

@Getter
@Builder
public class TaskDefinition {

    private TaskDefinitionId id;

    private TaskInfo taskInfo;

    @Builder.Default
    private TaskStatus taskStatus = TaskStatus.ACTIVE;

    @Builder.Default
    private Map<String, String> metadata = new HashMap<>();

    private HttpConfig httpConfig;

    private RetryPolicy retryPolicy;

    private Audit audit;

    public TaskDefinition(TaskDefinitionId id,
                          TaskInfo taskInfo,
                          TaskStatus status,
                          Map<String, String> metadata,
                          HttpConfig httpConfig,
                          RetryPolicy retryPolicy,
                          Audit audit) {
        if (isNull(id)) throw new DomainValidationException("id cannot be null");
        if (isNull(taskInfo)) throw new DomainValidationException("taskInfo cannot be null");
        if (isNull(status)) throw new DomainValidationException("taskStatus cannot be null");
        if (isNull(httpConfig)) throw new DomainValidationException("httpConfig cannot be null");
        if (isNull(retryPolicy)) throw new DomainValidationException("retryPolicy cannot be null");
        if (isNull(audit)) throw new DomainValidationException("audit cannot be null");

        this.id = id;
        this.taskInfo = taskInfo;
        this.taskStatus = status;
        this.metadata = metadata;
        this.httpConfig = httpConfig;
        this.retryPolicy = retryPolicy;
        this.audit = audit;
    }

    /**
     * Factory method to create a new TaskDefinition.
     *
     * @param taskInfo    The basic information about the task.
     * @param taskStatus  The initial status of the task (default is ACTIVE).
     * @param metadata    Optional metadata for the task (default is empty).
     * @param httpConfig  The HTTP configuration for the task.
     * @param retryPolicy The retry policy for the task.
     */
    public static TaskDefinition createNew(
            TaskInfo taskInfo,
            TaskStatus taskStatus,
            Map<String, String> metadata,
            HttpConfig httpConfig,
            RetryPolicy retryPolicy
    ) {
        return TaskDefinition.builder()
                .id(TaskDefinitionId.random())
                .taskInfo(taskInfo)
                .taskStatus(taskStatus)
                .metadata(metadata != null ? metadata : new HashMap<>())
                .httpConfig(httpConfig)
                .retryPolicy(retryPolicy)
                .audit(Audit.createNew())
                .build();
    }

    /**
     * Deactivates this task definition.
     */
    public void deactivate() {
        this.taskStatus = TaskStatus.INACTIVE;
        this.audit = this.audit.withUpdatedAt(Instant.now());
    }

    /**
     * Activates this task definition.
     */
    public void activate() {
        this.taskStatus = TaskStatus.ACTIVE;
        this.audit = this.audit.withUpdatedAt(Instant.now());
    }

    /**
     * Updates the HTTP configuration.
     *
     * @param newHttpConfig the new HTTP configuration
     */
    public void updateHttpConfig(HttpConfig newHttpConfig) {
        if (isNull(newHttpConfig)) throw new DomainValidationException("httpConfig cannot be null");

        this.httpConfig = newHttpConfig;
        this.audit = this.audit.withUpdatedAt(Instant.now());
    }

    /**
     * Updates the retry policy.
     *
     * @param newRetryPolicy the new retry policy
     */
    public void updateRetryPolicy(RetryPolicy newRetryPolicy) {
        if (isNull(newRetryPolicy)) throw new DomainValidationException("retryPolicy cannot be null");

        this.retryPolicy = newRetryPolicy;
        this.audit = this.audit.withUpdatedAt(Instant.now());
    }

    /**
     * Updates task metadata.
     *
     * @param newMetadata the new metadata map
     */
    public void updateMetadata(Map<String, String> newMetadata) {
        this.metadata = newMetadata != null ? new HashMap<>(newMetadata) : new HashMap<>();
        this.audit = this.audit.withUpdatedAt(Instant.now());
    }

    /**
     * Adds a single metadata entry.
     *
     * @param key   the metadata key
     * @param value the metadata value
     */
    public void addMetadata(String key, String value) {
        if (isNull(key)) throw new DomainValidationException("Metadata key cannot be null");

        this.metadata.put(key, value);
        this.audit = this.audit.withUpdatedAt(Instant.now());
    }

    /**
     * Removes a metadata entry.
     *
     * @param key the metadata key to remove
     */
    public void removeMetadata(String key) {
        this.metadata.remove(key);
        this.audit = this.audit.withUpdatedAt(Instant.now());
    }

    /**
     * Checks if this task is active.
     */
    public boolean isActive() {
        return TaskStatus.ACTIVE.equals(this.taskStatus);
    }
}


