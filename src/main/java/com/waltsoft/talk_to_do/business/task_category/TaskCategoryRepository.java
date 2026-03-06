package com.waltsoft.talk_to_do.business.task_category;


import com.waltsoft.talk_to_do.entity.task_category.TaskCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface TaskCategoryRepository extends JpaRepository<TaskCategory, Long> {
}
