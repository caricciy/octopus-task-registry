package com.octopus.application.port.inbound.command;

import com.octopus.domain.vo.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class CreateTaskDefinitionCommand {

    @NotBlank(message = "Task name cannot be blank")
    @Size(min = TaskInfo.TASK_NAME_MIN_LENGTH, max =  TaskInfo.TASK_NAME_MAX_LENGTH, message = "Task name must be between {min} and {max} characters")
    @Pattern(regexp = TaskInfo.TASK_NAME_PATTERN, message = "Task name can only contain letters, numbers, hyphens and underscores")
    private String name;

    @NotBlank(message = "Category cannot be blank")
    @Size(min = TaskInfo.CATEGORY_MIN_LENGTH, max = TaskInfo.CATEGORY_MAX_LENGTH, message = "Category must be between {min} and {max} characters")
    private String category;

    @Size(max = TaskInfo.DESCRIPTION_MAX_LENGTH, message = "Description cannot exceed {max} characters")
    private String description;

    @NotNull
    private String status;

    private Map<String, String> metadata;

    @Valid
    @NotNull(message = "HttpConfig cannot be null")
    private HttpConfigCommand httpConfig;

    @Valid
    @NotNull(message = "RetryPolicy cannot be null")
    private RetryPolicyCommand retryPolicy;

    @Getter
    @Setter
    @Builder
    public static class HttpConfigCommand {
        @NotBlank(message = "Endpoint cannot be blank")
        @Pattern(regexp = "^https?://.*", message = "Endpoint must be a valid HTTP or HTTPS URL")
        @Size(max = Endpoint.MAX_URL_LENGTH, message = "Endpoint cannot exceed {max} characters")
        private String endpoint;

        @NotBlank(message = "HTTP method cannot be blank")
        private String httpMethod;

        @NotNull(message = "Timeout seconds cannot be null")
        @Min(value = HttpConfig.MIN_TIMEOUT_SECONDS, message = "Timeout must be at least {value} second")
        @Max(value = HttpConfig.MAX_TIMEOUT_SECONDS, message = "Timeout cannot exceed {value} seconds")
        private Integer timeoutSeconds;

        private Map<String, String> headers;
        private Map<String, Object> payloadTemplate;
    }

    @Getter
    @Setter
    @Builder
    public static class RetryPolicyCommand {
        @NotNull(message = "Max attempts cannot be null")
        @Min(value = RetryPolicy.MIN_ATTEMPTS_LIMIT, message = "Max attempts must be at least {value}")
        @Max(value = RetryPolicy.MAX_ATTEMPTS_LIMIT, message = "Max attempts cannot exceed {value}")
        private Integer maxAttempts;

        private Integer[] backoffSeconds;
        private Integer[] retryableStatusCodes;
    }
}

