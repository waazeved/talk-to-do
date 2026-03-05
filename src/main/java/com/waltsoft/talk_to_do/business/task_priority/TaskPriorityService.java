package com.waltsoft.talk_to_do.business.task_priority;


import com.waltsoft.talk_to_do.business.basic.BasicService;
import com.waltsoft.talk_to_do.entity.task_priority.TaskPriority;
import com.waltsoft.talk_to_do.entity.task_priority.enums.TaskPriorityCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskPriorityService implements BasicService<TaskPriority, Long> {

    private final TaskPriorityRepository repository;
    private Map<TaskPriorityCodeEnum, TaskPriority> taskPriorityMappedByCode;

    @Autowired
    public TaskPriorityService(final TaskPriorityRepository repository) {
        this.repository = repository;
    }

    @Override
    public JpaRepository<TaskPriority, Long> getRepository() {
        return repository;
    }

    public TaskPriority findByCode(TaskPriorityCodeEnum code) {
        return this.taskPriorityMappedByCode.get(code);
    }

    @EventListener(ApplicationReadyEvent.class)
    void makeTaskPriorityMappedByCode() {
        this.taskPriorityMappedByCode = findAllMappedByCode();
    }

    Map<TaskPriorityCodeEnum, TaskPriority> findAllMappedByCode() {
        List<TaskPriority> taskPriorities = repository.findAll();

        return taskPriorities
                .stream()
                .collect(Collectors.toMap(TaskPriority::getCode, t -> t));
    }


}
