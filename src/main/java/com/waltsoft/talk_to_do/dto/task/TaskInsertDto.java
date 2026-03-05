package com.waltsoft.talk_to_do.dto.task;


import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.waltsoft.talk_to_do.entity.task_priority.enums.TaskPriorityCodeEnum;
import com.waltsoft.talk_to_do.entity.task_status.enums.TaskStatusCodeEnum;

public record TaskInsertDto(
        @JsonPropertyDescription("The status code. Required.")
        TaskStatusCodeEnum taskStatusCode,

        @JsonPropertyDescription("The category id. Required.")
        long taskCategoryId,

        @JsonPropertyDescription("The priority code. Required.")
        TaskPriorityCodeEnum taskPriorityCode,

        @JsonPropertyDescription("A short and concise title for the task. Required.")
        String title,

        @JsonPropertyDescription("Detailed description of the task. Optional.")
        String description,

        @JsonPropertyDescription("Scheduled start date time. Format: YYYY-MM-DDTHH:mm:SS. Required.")
        String startDateTime,

        @JsonPropertyDescription("Scheduled end date time. Format: YYYY-MM-DDTHH:mm:SS. Optional.")
        String endDateTime
) implements ITaskDto {
}