package com.waltsoft.talk_to_do.dto.task_category;


import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record TaskCategoryDto(
        @JsonPropertyDescription("Task category id")
        long id,

        @JsonPropertyDescription("Task category code (e.g., 'WORK', 'PERSONAL')")
        String code
) {
}