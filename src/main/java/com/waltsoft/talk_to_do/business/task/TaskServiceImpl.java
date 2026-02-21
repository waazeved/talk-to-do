package com.waltsoft.talk_to_do.business.task;


import com.waltsoft.talk_to_do.entity.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;

    @Autowired
    public TaskServiceImpl(final TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public JpaRepository<Task, Long> getRepository() {
        return repository;
    }

}
