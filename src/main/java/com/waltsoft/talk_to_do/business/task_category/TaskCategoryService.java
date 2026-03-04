package com.waltsoft.talk_to_do.business.task_category;


import com.waltsoft.talk_to_do.dto.task_category.TaskCategoryDto;
import com.waltsoft.talk_to_do.dto.task_category.TaskCategoryInsertDto;
import com.waltsoft.talk_to_do.entity.task_category.TaskCategory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TaskCategoryService {

    private static final String FIND_ALL_TASK_CATEGORY_DTO_CACHE = "findAllTaskCategoryDto";

    private final TaskCategoryRepository repository;
    private final ApplicationEventPublisher eventPublisher;
    private final CacheManager cacheManager;


    @Autowired
    public TaskCategoryService(final TaskCategoryRepository repository, ApplicationEventPublisher eventPublisher, CacheManager cacheManager) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
        this.cacheManager = cacheManager;
    }

    @Transactional
    @Tool(name = "insertNewTaskCategory", description = "Insert a new task category in the database")
    public String insert(TaskCategoryInsertDto insertDto) {

        TaskCategory category = new TaskCategory()
                .setCode(insertDto.code());

        repository.save(category);
        eventPublisher.publishEvent(category);

        return "New task category created with success.";
    }

    @Tool(name = "findAllTaskCategory", description = "Find all existent task categories in the database")
    @Cacheable(value = FIND_ALL_TASK_CATEGORY_DTO_CACHE)
    public Set<TaskCategoryDto> findAllDto() {
        List<TaskCategory> categories = repository.findAll();

        return categories
                .stream()
                .map(TaskCategoryDto::new)
                .collect(Collectors.toSet());
    }

    public Optional<TaskCategory> findById(long id) {
        return repository.findById(id);
    }

    @TransactionalEventListener(classes = {TaskCategoryService.class})
    public void clearFindAllDtoCache() {
        cacheManager
                .getCache(FIND_ALL_TASK_CATEGORY_DTO_CACHE)
                .clear();
    }
}
