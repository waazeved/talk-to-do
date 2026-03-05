package com.waltsoft.talk_to_do.business.task_priority;


import com.waltsoft.talk_to_do.entity.task_priority.TaskPriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface TaskPriorityRepository extends JpaRepository<TaskPriority, Long> {
    
}
