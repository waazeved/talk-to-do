package com.waltsoft.talk_to_do.business.task_priority;


import com.waltsoft.talk_to_do.business.basic.BasicService;
import com.waltsoft.talk_to_do.entity.task_priority.TaskPriority;
import com.waltsoft.talk_to_do.entity.task_priority.enums.TaskPriorityCodeEnum;

public interface TaskPriorityService extends BasicService<TaskPriority, Long> {

    TaskPriority findByCode(TaskPriorityCodeEnum code);
}
