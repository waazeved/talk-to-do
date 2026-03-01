package com.waltsoft.talk_to_do.business.task_status;


import com.waltsoft.talk_to_do.business.basic.BasicService;
import com.waltsoft.talk_to_do.entity.task_status.TaskStatus;
import com.waltsoft.talk_to_do.entity.task_status.enums.TaskStatusCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskStatusService implements BasicService<TaskStatus, Long> {

    private final TaskStatusRepository repository;

    @Autowired
    public TaskStatusService(final TaskStatusRepository repository) {
        this.repository = repository;
    }

    @Override
    public JpaRepository<TaskStatus, Long> getRepository() {
        return repository;
    }

    public TaskStatus findByCode(TaskStatusCodeEnum code) {
        List<TaskStatus> all = findAll();
        return all
                .stream()
                .filter(p -> p
                        .getCode()
                        .equals(code))
                .findFirst()
                .get();
    }

}
