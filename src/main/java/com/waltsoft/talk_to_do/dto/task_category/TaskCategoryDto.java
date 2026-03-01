package com.waltsoft.talk_to_do.dto.task_category;


import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.waltsoft.talk_to_do.entity.task_category.TaskCategory;

public record TaskCategoryDto(
        @JsonPropertyDescription("Task category id")
        long id,

        @JsonPropertyDescription("Task category code (e.g., 'WORK', 'PERSONAL')")
        String code


) {
    public TaskCategoryDto(TaskCategory category) {
        this(category.getId(), category.getCode());
    }
}