package com.waltsoft.talk_to_do.business.task;


import com.waltsoft.talk_to_do.business.basic.BasicService;
import com.waltsoft.talk_to_do.business.task_category.TaskCategoryService;
import com.waltsoft.talk_to_do.business.task_priority.TaskPriorityService;
import com.waltsoft.talk_to_do.business.task_status.TaskStatusService;
import com.waltsoft.talk_to_do.dto.task.ITaskDto;
import com.waltsoft.talk_to_do.dto.task.TaskDto;
import com.waltsoft.talk_to_do.dto.task.TaskInsertDto;
import com.waltsoft.talk_to_do.dto.task.TaskUpdateDto;
import com.waltsoft.talk_to_do.entity.task.Task;
import com.waltsoft.talk_to_do.entity.task_category.TaskCategory;
import com.waltsoft.talk_to_do.entity.task_priority.TaskPriority;
import com.waltsoft.talk_to_do.entity.task_status.TaskStatus;
import com.waltsoft.talk_to_do.exceptions.ai.InvalidEntityPropertyException;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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

    private static final String INSERTED_SUCCESS_MESSAGE = "New task created with success.";
    private static final String UPDATED_SUCCESS_MESSAGE = "Task updated with success.";

    private final TaskRepository repository;
    private final TaskStatusService taskStatusService;
    private final TaskCategoryService taskCategoryService;
    private final TaskPriorityService taskPriorityService;
    private final ApplicationEventPublisher eventPublisher;


    @Autowired
    public TaskService(final TaskRepository repository, TaskStatusService taskStatusService, TaskCategoryService taskCategoryService, TaskPriorityService taskPriorityService, ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.taskStatusService = taskStatusService;
        this.taskCategoryService = taskCategoryService;
        this.taskPriorityService = taskPriorityService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public JpaRepository<Task, Long> getRepository() {
        return repository;
    }

    @Transactional
    @Tool(name = "insertNewTask", description = "Insert a new task in the database")
    public String insert(TaskInsertDto insertDto) {
        Task task = new Task();
        buildTask(task, insertDto);
        repository.save(task);
        eventPublisher.publishEvent(task);
        return INSERTED_SUCCESS_MESSAGE;
    }

    @Transactional
    @Tool(name = "updateTask", description = "Update a new task in the database")
    public String update(TaskUpdateDto updateDto) {
        Optional<Task> taskOptional = repository.findById(updateDto.id());

        if (taskOptional.isEmpty()) {
            throw new InvalidEntityPropertyException("Invalid task id");
        }

        Task task = taskOptional.get();
        buildTask(task, updateDto);
        repository.save(task);
        eventPublisher.publishEvent(task);
        return UPDATED_SUCCESS_MESSAGE;
    }

    void buildTask(Task task, ITaskDto taskDto) {
        TaskPriority priority = this.taskPriorityService.findByCode(taskDto.taskPriorityCode());
        TaskStatus status = this.taskStatusService.findByCode(taskDto.taskStatusCode());

        Optional<TaskCategory> categoryOptional = this.taskCategoryService
                .findById(taskDto.taskCategoryId());

        if (categoryOptional.isEmpty()) {
            throw new InvalidEntityPropertyException("Invalid tasCategoryId");
        }

        TaskCategory category = categoryOptional.get();

        task
                .setTitle(taskDto.title())
                .setDescription(taskDto.description())
                .setPriority(priority)
                .setStatus(status)
                .setStartDateTime(LocalDateTime.parse(taskDto.startDateTime()))
                .setEndDateTime(LocalDateTime.parse(taskDto.endDateTime()))
                .setCategory(category);

    }

    @Tool(name = "findAllTask", description = "Find all existent tasks in the database")
    @Transactional(readOnly = true)
    public Set<TaskDto> findAllDto() {
        List<Task> tasks = repository.findAll();

        return tasks
                .stream()
                .map(TaskDto::new)
                .collect(Collectors.toSet());
    }
}
