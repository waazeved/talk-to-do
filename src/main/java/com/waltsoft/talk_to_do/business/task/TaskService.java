package com.waltsoft.talk_to_do.business.task;


import com.waltsoft.talk_to_do.business.basic.BasicService;
import com.waltsoft.talk_to_do.dto.task.TaskInsertDto;
import com.waltsoft.talk_to_do.entity.task.Task;
import org.springframework.ai.tool.annotation.Tool;

public interface TaskService extends BasicService<Task, Long> {

    @Tool(name = "insertNewTask", description = "Insert a new task in the database")
    String insertAITool(TaskInsertDto insertDto);
}
