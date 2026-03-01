package com.waltsoft.talk_to_do.business.task;


import com.waltsoft.talk_to_do.business.task_category.TaskCategoryService;
import com.waltsoft.talk_to_do.business.task_priority.TaskPriorityService;
import com.waltsoft.talk_to_do.business.task_status.TaskStatusService;
import com.waltsoft.talk_to_do.dto.task.TaskInsertDto;
import com.waltsoft.talk_to_do.entity.task.Task;
import com.waltsoft.talk_to_do.entity.task_priority.TaskPriority;
import com.waltsoft.talk_to_do.entity.task_status.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;
    private final TaskStatusService taskStatusService;
    private final TaskCategoryService taskCategoryService;
    private final TaskPriorityService taskPriorityService;

    @Autowired
    public TaskServiceImpl(final TaskRepository repository, TaskStatusService taskStatusService, TaskCategoryService taskCategoryService, TaskPriorityService taskPriorityService) {
        this.repository = repository;
        this.taskStatusService = taskStatusService;
        this.taskCategoryService = taskCategoryService;
        this.taskPriorityService = taskPriorityService;
    }

    @Override
    public String insertAITool(TaskInsertDto insertDto) {
        insert(insertDto);
        return "New task created with success.";
    }

    @Transactional
    void insert(TaskInsertDto insertDto) {

        TaskPriority priority = this.taskPriorityService.findByCode(insertDto.taskPriorityCode());
        TaskStatus status = this.taskStatusService.findByCode(insertDto.taskStatusCode());
        String dateTimeFormat = "";

        Task task = new Task()
                .setTitle(insertDto.title())
                .setDescription(insertDto.description())
                .setPriority(priority)
                .setStatus(status)
                .setStartDateTime(LocalDateTime.parse(insertDto.startDateTime()))
                .setEndDateTime(LocalDateTime.parse(insertDto.endDateTime()))
                .setCategory(this.taskCategoryService
                        .findAll()
                        .getFirst());

        save(task);

    }

    @Override
    public JpaRepository<Task, Long> getRepository() {
        return repository;
    }

}
