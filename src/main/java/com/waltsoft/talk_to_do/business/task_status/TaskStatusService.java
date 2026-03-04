package com.waltsoft.talk_to_do.business.task_status;


import com.waltsoft.talk_to_do.entity.task_status.TaskStatus;
import com.waltsoft.talk_to_do.entity.task_status.enums.TaskStatusCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskStatusService {

    private final TaskStatusRepository repository;
    private Map<TaskStatusCodeEnum, TaskStatus> taskStatusMappedByCode;

    @Autowired
    public TaskStatusService(final TaskStatusRepository repository) {
        this.repository = repository;
    }

    public TaskStatus findByCode(TaskStatusCodeEnum code) {
        return taskStatusMappedByCode.get(code);
    }

    @EventListener(ApplicationReadyEvent.class)
    void makeTaskStatusMappedByCode() {
        this.taskStatusMappedByCode = findAllMappedByCode();
    }

    Map<TaskStatusCodeEnum, TaskStatus> findAllMappedByCode() {
        List<TaskStatus> taskStatusList = repository.findAll();

        return taskStatusList
                .stream()
                .collect(Collectors.toMap(TaskStatus::getCode, t -> t));
    }
}
