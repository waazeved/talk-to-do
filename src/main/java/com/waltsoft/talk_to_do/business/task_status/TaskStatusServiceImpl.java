package com.waltsoft.talk_to_do.business.task_status;


import com.waltsoft.talk_to_do.entity.task_status.TaskStatus;
import com.waltsoft.talk_to_do.entity.task_status.enums.TaskStatusCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class TaskStatusServiceImpl implements TaskStatusService {

    private final TaskStatusRepository repository;

    @Autowired
    public TaskStatusServiceImpl(final TaskStatusRepository repository) {
        this.repository = repository;
    }

    @Override
    public JpaRepository<TaskStatus, Long> getRepository() {
        return repository;
    }

    @Override
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
