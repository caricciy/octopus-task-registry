package com.octopus.adapter.inbound.rest.dto;

public record CreateTaskDefinitionResponse(String id,
                                           String name,
                                           String category,
                                           String description,
                                           String status
) {

}
