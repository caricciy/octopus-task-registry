package com.octopus.domain.entity;

import com.octopus.domain.exception.DomainValidationException;
import com.octopus.domain.vo.*;
import lombok.Builder;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

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
        this.setId(id);
        this.setTaskInfo(taskInfo);
        this.setTaskStatus(status);
        this.setMetadata(metadata);
        this.setHttpConfig(httpConfig);
        this.setRetryPolicy(retryPolicy);
        this.setAudit(audit);
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
                .metadata(metadata)
                .httpConfig(httpConfig)
                .retryPolicy(retryPolicy)
                .audit(Audit.createNew())
                .build();
    }

    /**
     * Deactivates this task definition.
     */
    public void deactivate() {
        this.setTaskStatus(TaskStatus.INACTIVE);
        this.markAsModified();
    }

    /**
     * Activates this task definition.
     */
    public void activate() {
        this.setTaskStatus(TaskStatus.ACTIVE);
        this.markAsModified();
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
        this.markAsModified();
    }

    /**
     * Removes a metadata entry.
     *
     * @param key the metadata key to remove
     */
    public void removeMetadata(String key) {
        this.metadata.remove(key);
        this.markAsModified();
    }

    /**
     * Checks if this task is active.
     */
    public boolean isActive() {
        return TaskStatus.ACTIVE.equals(this.taskStatus);
    }

    /**
     * Marks this task definition as modified by updating the audit information.
     */
    private void markAsModified() {
        this.setAudit(this.audit.withUpdatedAt(Instant.now()));
    }

    /**
     * Updates the HTTP configuration.
     *
     * @param newHttpConfig the new HTTP configuration
     */
    public void changeHttpConfig(HttpConfig newHttpConfig) {
        this.setHttpConfig(newHttpConfig);
        this.markAsModified();
    }

    /**
     * Updates the retry policy.
     *
     * @param newRetryPolicy the new retry policy
     */
    public void changeRetryPolicy(RetryPolicy newRetryPolicy) {
        this.setRetryPolicy(newRetryPolicy);
        this.markAsModified();
    }

    /**
     * Updates task metadata.
     *
     * @param newMetadata the new metadata map
     */
    public void changeMetadata(Map<String, String> newMetadata) {
        this.setMetadata(newMetadata);
        this.markAsModified();
    }

    public TaskDefinitionId id() {
        return id;
    }

    public TaskInfo taskInfo() {
        return taskInfo;
    }

    public TaskStatus taskStatus() {
        return taskStatus;
    }

    public Map<String, String> metadata() {
        return metadata;
    }

    public HttpConfig httpConfig() {
        return httpConfig;
    }

    public RetryPolicy retryPolicy() {
        return retryPolicy;
    }

    public Audit audit() {
        return audit;
    }

    private void setId(TaskDefinitionId id) {
        if (isNull(id)) throw new DomainValidationException("id cannot be null");

        this.id = id;
    }

    private void setTaskInfo(TaskInfo taskInfo) {
        if (isNull(taskInfo)) throw new DomainValidationException("taskInfo cannot be null");

        this.taskInfo = taskInfo;
    }

    private void setTaskStatus(TaskStatus taskStatus) {
        if (isNull(taskStatus)) throw new DomainValidationException("taskStatus cannot be null");

        this.taskStatus = taskStatus;
    }

    private void setMetadata(Map<String, String> metadata) {
        if (isNull(metadata)) {
            this.metadata = new HashMap<>();
            return;
        }
        this.metadata = metadata;
    }

    private void setHttpConfig(HttpConfig httpConfig) {
        if (isNull(httpConfig)) throw new DomainValidationException("httpConfig cannot be null");

        this.httpConfig = httpConfig;
    }

    private void setRetryPolicy(RetryPolicy retryPolicy) {
        if (isNull(retryPolicy)) throw new DomainValidationException("retryPolicy cannot be null");

        this.retryPolicy = retryPolicy;
    }

    private void setAudit(Audit audit) {
        if (isNull(audit)) throw new DomainValidationException("audit cannot be null");

        this.audit = audit;
    }
}


