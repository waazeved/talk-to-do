package com.waltsoft.talk_to_do.business.task_status;


import com.waltsoft.talk_to_do.entity.task_status.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {
}
