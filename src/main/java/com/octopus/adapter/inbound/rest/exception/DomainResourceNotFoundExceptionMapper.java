package com.octopus.adapter.inbound.rest.exception;

import com.octopus.adapter.inbound.rest.dto.ErrorResponse;
import com.octopus.domain.exception.DomainResourceNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@Provider
public class DomainResourceNotFoundExceptionMapper implements ExceptionMapper<DomainResourceNotFoundException> {

    @Override
    public Response toResponse(DomainResourceNotFoundException exception) {

        log.warn("Domain resource not found exception occurred: {}", exception.getMessage(), exception);

        var errorResponse = ErrorResponse.builder()
                .title(exception.getClass().getSimpleName())
                .status(Response.Status.NOT_FOUND.getStatusCode())
                .violations(Map.of("message", exception.getMessage()))
                .timestamp(java.time.Instant.now())
                .build();

        return Response.status(Response.Status.NOT_FOUND)
                .entity(errorResponse)
                .build();
    }

}
