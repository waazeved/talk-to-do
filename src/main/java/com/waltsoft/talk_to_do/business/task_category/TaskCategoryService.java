package com.waltsoft.talk_to_do.business.task_category;


import com.waltsoft.talk_to_do.business.basic.BasicService;
import com.waltsoft.talk_to_do.dto.task_category.TaskCategoryInsertDto;
import com.waltsoft.talk_to_do.entity.task_category.TaskCategory;
import org.springframework.ai.tool.annotation.Tool;

public interface TaskCategoryService extends BasicService<TaskCategory, Long> {
    @Tool(name = "insertNewTaskCategory", description = "Insert a new task category in the database")
    String insertAITool(TaskCategoryInsertDto insertDto);

    @Tool(name = "listAllTaskCategory", description = "List all existent task categories in the database")
    String listAllAITool();
}
