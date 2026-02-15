package com.octopus.domain.vo;

import com.octopus.domain.exception.DomainValidationException;
import lombok.Builder;

import static java.util.Objects.isNull;

@Builder
public record TaskInfo(String name,
                       String category,
                       String description) {

    public static final int TASK_NAME_MIN_LENGTH = 3;
    public static final int TASK_NAME_MAX_LENGTH = 100;
    public static final int CATEGORY_MIN_LENGTH = 2;
    public static final int CATEGORY_MAX_LENGTH = 50;
    public static final int DESCRIPTION_MAX_LENGTH = 500;
    public static final String TASK_NAME_PATTERN = "^[a-zA-Z0-9-_]+$";

    public TaskInfo {
        if (isNull(name)) throw new DomainValidationException("Task name cannot be null");
        if (isNull(category)) throw new DomainValidationException("Category cannot be null");
        if (isNull(description)) throw new DomainValidationException("Description cannot be null");

        if (name.isBlank()) throw new DomainValidationException("Task name cannot be blank");
        if (name.length() < TASK_NAME_MIN_LENGTH || name.length() > TASK_NAME_MAX_LENGTH) throw new DomainValidationException("Task name must be between 3 and 100 characters");
        if (!name.matches(TASK_NAME_PATTERN)) throw new DomainValidationException("Task name can only contain letters, numbers, hyphens and underscores");

        if (category.isBlank()) throw new DomainValidationException("Category cannot be blank");
        if (category.length() < CATEGORY_MIN_LENGTH || category.length() > CATEGORY_MAX_LENGTH) throw new DomainValidationException("Category must be between 2 and 50 characters");

        if (description.length() > DESCRIPTION_MAX_LENGTH) throw new DomainValidationException("Description cannot exceed 500 characters");

    }
}
