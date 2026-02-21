package com.waltsoft.talk_to_do.business.task_priority;


import com.waltsoft.talk_to_do.entity.task_priority.TaskPriority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
class TaskPriorityServiceImpl implements TaskPriorityService {

    private final TaskPriorityRepository repository;

    @Autowired
    public TaskPriorityServiceImpl(final TaskPriorityRepository repository) {
        this.repository = repository;
    }

    @Override
    public JpaRepository<TaskPriority, Long> getRepository() {
        return repository;
    }

}
