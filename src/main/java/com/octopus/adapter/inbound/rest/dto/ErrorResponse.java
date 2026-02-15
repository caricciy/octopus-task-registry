package com.octopus.adapter.inbound.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.Instant;
import java.util.Map;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(String title,
                            int status,
                            String message,
                            Map<String, Object> violations,
                            Instant timestamp) {
}

