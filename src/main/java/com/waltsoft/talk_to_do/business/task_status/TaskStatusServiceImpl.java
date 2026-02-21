package com.waltsoft.talk_to_do.business.task_status;


import com.waltsoft.talk_to_do.entity.task_status.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

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

}
