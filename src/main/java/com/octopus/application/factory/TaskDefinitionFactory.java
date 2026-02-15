package com.octopus.application.factory;

import com.octopus.application.port.inbound.command.CreateTaskDefinitionCommand;
import com.octopus.domain.entity.TaskDefinition;
import com.octopus.domain.vo.*;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TaskDefinitionFactory {

    /**
     * Creates a TaskDefinition domain entity from an application command.
     */
    public TaskDefinition createFrom(CreateTaskDefinitionCommand command) {
        var taskInfo = buildTaskInfo(command);
        var httpConfig = buildHttpConfig(command.getHttpConfig());
        var retryPolicy = buildRetryPolicy(command.getRetryPolicy());

        return TaskDefinition.createNew(
                taskInfo,
                TaskStatus.fromString(command.getStatus()),
                command.getMetadata(),
                httpConfig,
                retryPolicy
        );
    }

    private TaskInfo buildTaskInfo(CreateTaskDefinitionCommand command) {
        return TaskInfo.builder()
                .name(command.getName())
                .category(command.getCategory())
                .description(command.getDescription())
                .build();
    }

    private HttpConfig buildHttpConfig(CreateTaskDefinitionCommand.HttpConfigCommand cmd) {
        return HttpConfig.builder()
                .endpoint(Endpoint.of(cmd.getEndpoint()))
                .httpMethod(HttpMethod.fromString(cmd.getHttpMethod()))
                .timeoutSeconds(cmd.getTimeoutSeconds())
                .headers(cmd.getHeaders())
                .payloadTemplate(cmd.getPayloadTemplate())
                .build();
    }

    private RetryPolicy buildRetryPolicy(CreateTaskDefinitionCommand.RetryPolicyCommand cmd) {
        return RetryPolicy.builder()
                .maxAttempts(cmd.getMaxAttempts())
                .backoffSeconds(cmd.getBackoffSeconds())
                .retryableStatusCodes(cmd.getRetryableStatusCodes())
                .build();
    }
}
