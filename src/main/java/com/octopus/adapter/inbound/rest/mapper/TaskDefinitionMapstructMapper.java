package com.octopus.adapter.inbound.rest.mapper;

import com.octopus.adapter.inbound.rest.dto.CreateTaskDefinitionResponse;
import com.octopus.domain.entity.TaskDefinition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(
        componentModel = "jakarta-cdi",
        unmappedTargetPolicy = IGNORE
)
public interface TaskDefinitionMapstructMapper {

    @Mapping(target = "id", expression = "java(domain.id().value().toString())")
    @Mapping(target = "name", expression = "java(domain.taskInfo().name())")
    @Mapping(target = "category", expression = "java(domain.taskInfo().category())")
    @Mapping(target = "description", expression = "java(domain.taskInfo().description())")
    @Mapping(target = "status", expression = "java(domain.taskStatus().name())")
    CreateTaskDefinitionResponse toResponse(TaskDefinition domain);

}
