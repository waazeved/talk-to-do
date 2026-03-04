package com.waltsoft.talk_to_do.dto.task;

import com.waltsoft.talk_to_do.entity.task_priority.enums.TaskPriorityCodeEnum;
import com.waltsoft.talk_to_do.entity.task_status.enums.TaskStatusCodeEnum;

public interface ITaskDto {
 
    String title();

    String description();

    TaskStatusCodeEnum taskStatusCode();

    long taskCategoryId();

    TaskPriorityCodeEnum taskPriorityCode();

    String startDateTime();

    String endDateTime();
}