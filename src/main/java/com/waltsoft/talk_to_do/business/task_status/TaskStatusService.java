package com.waltsoft.talk_to_do.business.task_status;


import com.waltsoft.talk_to_do.business.basic.BasicService;
import com.waltsoft.talk_to_do.entity.task_status.TaskStatus;
import com.waltsoft.talk_to_do.entity.task_status.enums.TaskStatusCodeEnum;

public interface TaskStatusService extends BasicService<TaskStatus, Long> {
    TaskStatus findByCode(TaskStatusCodeEnum code);
}
