package com.octopus.application.port.inbound;

public interface ResolveTaskDefinitionQuery {

    /**
     * Resolves a task definition by its name.
     *
     * @param name the name of the task definition to resolve
     * @return true if the task definition exists and is active, false otherwise.
     */
    boolean execute(String name);
}
