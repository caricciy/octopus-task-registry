package com.octopus.adapter.inbound.rest;


import com.octopus.adapter.inbound.rest.mapper.TaskDefinitionMapstructMapper;
import com.octopus.application.port.inbound.CreateTaskDefinitionUseCase;
import com.octopus.application.port.inbound.command.CreateTaskDefinitionCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static jakarta.ws.rs.core.Response.Status.CREATED;

@Slf4j
@Path("/api/v1/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class TaskDefinitionRestAdapter {

    private final CreateTaskDefinitionUseCase createTaskUseCase;
    private final TaskDefinitionMapstructMapper mapper;

    /**
     * Endpoint to create a new task definition.
     *
     * @param request the request body containing task definition data
     * @return HTTP 201 Created with the created task definition in the response body.
     */
    @POST
    public Response createTask(@Valid @NotNull CreateTaskDefinitionCommand request) {
        log.info("REST: Creating task with name: {}, category: {}", request.getName(), request.getCategory());

        var domain = createTaskUseCase.execute(request);

        var response = mapper.toResponse(domain);

        return Response.status(CREATED).entity(response).build();
    }
}
