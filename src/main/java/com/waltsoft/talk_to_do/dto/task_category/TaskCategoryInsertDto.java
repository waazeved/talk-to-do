package com.waltsoft.talk_to_do.dto.task_category;


import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record TaskCategoryInsertDto(
        @JsonPropertyDescription("New category code (e.g., 'WORK', 'PERSONAL'). Can't be null. It can't be an already existent category code in the database.")
        String code
) {
}