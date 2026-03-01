package com.waltsoft.talk_to_do.dto.task;


import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.waltsoft.talk_to_do.entity.task.Task;
import com.waltsoft.talk_to_do.entity.task_priority.enums.TaskPriorityCodeEnum;
import com.waltsoft.talk_to_do.entity.task_status.enums.TaskStatusCodeEnum;

public record TaskDto(

        @JsonPropertyDescription("Task id")
        long id,

        @JsonPropertyDescription("A short and concise title for the task.")
        String title,

        @JsonPropertyDescription("Detailed description of the task.")
        String description,

        @JsonPropertyDescription("The status code.")
        TaskStatusCodeEnum taskStatusCode,

        @JsonPropertyDescription("The category id.")
        long taskCategoryId,

        @JsonPropertyDescription("The priority code.")
        TaskPriorityCodeEnum taskPriorityCode,

        @JsonPropertyDescription("Scheduled start date time. Format: YYYY-MM-DDTHH:mm:SS.")
        String startDateTime,

        @JsonPropertyDescription("Scheduled end date time. Format: YYYY-MM-DDTHH:mm:SS.")
        String endDateTime


) {
    public TaskDto(Task task) {
        this(task.getId(), task.getTitle(), task.getDescription(),
                task
                        .getStatus()
                        .getCode(),
                task
                        .getCategory()
                        .getId(),
                task
                        .getPriority()
                        .getCode(),
                task
                        .getStartDateTime()
                        .toString(),
                task
                        .getEndDateTime()
                        !=null ? task
                        .getEndDateTime()
                        .toString():null);
    }
}