package com.waltsoft.talk_to_do.business.task;


import com.waltsoft.talk_to_do.entity.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface TaskRepository extends JpaRepository<Task, Long> {
}
