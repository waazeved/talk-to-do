package com.waltsoft.talk_to_do.business.task_category;


import com.waltsoft.talk_to_do.entity.task_category.TaskCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

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

}
