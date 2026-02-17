package com.octopus.adapter.inbound.rest;


import com.octopus.application.port.inbound.ResolveTaskDefinitionQuery;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/api/internal/v1/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class TaskDefinitionInternalRestAdapter {

    private final ResolveTaskDefinitionQuery resolveTaskDefinitionQuery;

    /**
     * Endpoint to check if a task definition with the given name exists.
     *
     * @param name the name of the task definition to check
     * @return HTTP 200 OK if the task definition exists, HTTP 404 Not Found if it does not exist.
     */
    @HEAD
    @Path("/{name}")
    public Response checkTaskExists(@NotNull @NotBlank @PathParam("name") String name) {
        log.info("Checking task existence for '{}'", name);

        var exists = resolveTaskDefinitionQuery.execute(name);

        if( exists ) return Response.ok().build();

        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
