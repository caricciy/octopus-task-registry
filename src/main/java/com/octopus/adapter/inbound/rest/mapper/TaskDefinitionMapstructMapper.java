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

    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "name", source = "taskInfo.name")
    @Mapping(target = "category", source = "taskInfo.category")
    @Mapping(target = "description", source = "taskInfo.description")
    @Mapping(target = "status", source = "taskStatus")
    CreateTaskDefinitionResponse toResponse(TaskDefinition domain);

}
