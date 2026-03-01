package com.waltsoft.talk_to_do.business.task_category;


import com.waltsoft.talk_to_do.dto.task_category.TaskCategoryInsertDto;
import com.waltsoft.talk_to_do.entity.task_category.TaskCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class TaskCategoryServiceImpl implements TaskCategoryService {

    private final TaskCategoryRepository repository;

    @Autowired
    public TaskCategoryServiceImpl(final TaskCategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public JpaRepository<TaskCategory, Long> getRepository() {
        return repository;
    }

    @Override
    public String insertAITool(TaskCategoryInsertDto insertDto) {
        insert(insertDto);
        return "New task category created with success.";
    }


    @Transactional
    void insert(TaskCategoryInsertDto insertDto) {

        TaskCategory category = new TaskCategory()
                .setCode(insertDto.code());

        save(category);

    }

    @Override
    public String listAllAITool() {
        return "";
    }

}
