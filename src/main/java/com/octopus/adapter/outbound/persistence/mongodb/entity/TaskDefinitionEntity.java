package com.octopus.adapter.outbound.persistence.mongodb.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonId;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * MongoDB Entity for TaskDefinition.
 * This is the persistence model, separate from the domain model.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@MongoEntity(collection = "task_definitions")
public class TaskDefinitionEntity {

    @BsonId
    private String id;
    private String name;
    private String category;
    private String description;
    private String status;  // Stored as String in MongoDB
    private Map<String, String> metadata;
    private HttpConfigEntity httpConfig;
    private RetryPolicyEntity retryPolicy;
    private Instant createdAt;
    private Instant updatedAt;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class HttpConfigEntity {
        private String endpoint;
        private String httpMethod;  // Stored as String
        private Integer timeoutSeconds;
        private Map<String, String> headers;
        private Map<String, Object> payloadTemplate;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RetryPolicyEntity {
        private Integer maxAttempts;
        private List<Integer> backoffSeconds;
        private List<Integer> retryableStatusCodes;
    }
}

