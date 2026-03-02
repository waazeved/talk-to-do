package com.waltsoft.talk_to_do.business.task;


import com.waltsoft.talk_to_do.Exceptions.ai.InvalidEntityPropertyException;
import com.waltsoft.talk_to_do.business.basic.BasicService;
import com.waltsoft.talk_to_do.business.task_category.TaskCategoryService;
import com.waltsoft.talk_to_do.business.task_priority.TaskPriorityService;
import com.waltsoft.talk_to_do.business.task_status.TaskStatusService;
import com.waltsoft.talk_to_do.dto.task.TaskDto;
import com.waltsoft.talk_to_do.dto.task.TaskInsertDto;
import com.waltsoft.talk_to_do.dto.task.TaskUpdateDto;
import com.waltsoft.talk_to_do.entity.task.Task;
import com.waltsoft.talk_to_do.entity.task_priority.TaskPriority;
import com.waltsoft.talk_to_do.entity.task_status.TaskStatus;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TaskService implements BasicService<Task, Long> {

    private final TaskRepository repository;
    private final TaskStatusService taskStatusService;
    private final TaskCategoryService taskCategoryService;
    private final TaskPriorityService taskPriorityService;
    private final CacheManager cacheManager;

    @Autowired
    public TaskService(final TaskRepository repository, TaskStatusService taskStatusService, TaskCategoryService taskCategoryService, TaskPriorityService taskPriorityService, CacheManager cacheManager) {
        this.repository = repository;
        this.taskStatusService = taskStatusService;
        this.taskCategoryService = taskCategoryService;
        this.taskPriorityService = taskPriorityService;
        this.cacheManager = cacheManager;
    }

    @Override
    public JpaRepository<Task, Long> getRepository() {
        return repository;
    }


    @Transactional
    @Tool(name = "insertNewTask", description = "Insert a new task in the database")
    public String insert(TaskInsertDto insertDto) {

        TaskPriority priority = this.taskPriorityService.findByCode(insertDto.taskPriorityCode());
        TaskStatus status = this.taskStatusService.findByCode(insertDto.taskStatusCode());

        Task task = new Task()
                .setTitle(insertDto.title())
                .setDescription(insertDto.description())
                .setPriority(priority)
                .setStatus(status)
                .setStartDateTime(LocalDateTime.parse(insertDto.startDateTime()))
                .setEndDateTime(LocalDateTime.parse(insertDto.endDateTime()))
                .setCategory(this.taskCategoryService
                        .findById(insertDto.taskCategoryId())
                        .get());

        repository.save(task);

        cacheManager
                .getCache("aiChatCache")
                .clear();

        return "New task created with success.";

    }

    @Transactional
    @Tool(name = "updateTask", description = "Update a new task in the database")
    public String update(TaskUpdateDto updateDto) {

        Optional<Task> taskOptional = findById(updateDto.id());

        if (taskOptional.isEmpty()) {
            throw new InvalidEntityPropertyException("Invalid task id");
        }

        TaskPriority priority = this.taskPriorityService.findByCode(updateDto.taskPriorityCode());
        TaskStatus status = this.taskStatusService.findByCode(updateDto.taskStatusCode());

        Task task = taskOptional
                .get()
                .setTitle(updateDto.title())
                .setDescription(updateDto.description())
                .setPriority(priority)
                .setStatus(status)
                .setStartDateTime(LocalDateTime.parse(updateDto.startDateTime()))
                .setEndDateTime(LocalDateTime.parse(updateDto.endDateTime()))
                .setCategory(this.taskCategoryService
                        .findById(updateDto.taskCategoryId())
                        .get());

        repository.save(task);

        cacheManager
                .getCache("aiChatCache")
                .clear();

        return "New task created with success.";

    }

    @Tool(name = "findAllTask", description = "Find all existent tasks in the database")
    @Transactional(readOnly = true)
    public Set<TaskDto> findAllDto() {
        List<Task> categories = findAll();

        return categories
                .stream()
                .map(TaskDto::new)
                .collect(Collectors.toSet());
    }

}
