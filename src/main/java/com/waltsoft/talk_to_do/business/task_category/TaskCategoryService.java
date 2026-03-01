package com.waltsoft.talk_to_do.business.task_category;


import com.waltsoft.talk_to_do.business.basic.BasicService;
import com.waltsoft.talk_to_do.dto.task_category.TaskCategoryDto;
import com.waltsoft.talk_to_do.dto.task_category.TaskCategoryInsertDto;
import com.waltsoft.talk_to_do.entity.task_category.TaskCategory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TaskCategoryService implements BasicService<TaskCategory, Long> {

    private final TaskCategoryRepository repository;

    @Autowired
    public TaskCategoryService(final TaskCategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public JpaRepository<TaskCategory, Long> getRepository() {
        return repository;
    }

    @Transactional
    @Tool(name = "insertNewTaskCategory", description = "Insert a new task category in the database")
    public String insert(TaskCategoryInsertDto insertDto) {

        TaskCategory category = new TaskCategory()
                .setCode(insertDto.code());

        repository.save(category);

        return "New task category created with success.";
    }

    @Tool(name = "findAllTaskCategory", description = "Find all existent task categories in the database")
    public Set<TaskCategoryDto> findAllDto() {
        List<TaskCategory> categories = findAll();

        return categories
                .stream()
                .map(TaskCategoryDto::new)
                .collect(Collectors.toSet());
    }

}
