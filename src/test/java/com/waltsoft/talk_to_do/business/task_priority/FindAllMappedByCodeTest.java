package com.waltsoft.talk_to_do.business.task_priority;

import com.waltsoft.talk_to_do.business.ai_agent.AIAgentService;
import com.waltsoft.talk_to_do.entity.task_priority.TaskPriority;
import com.waltsoft.talk_to_do.entity.task_priority.enums.TaskPriorityCodeEnum;
import com.waltsoft.talk_to_do.test_container.PostgreSQLContainerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Map;


class FindAllMappedByCodeTest extends PostgreSQLContainerTest {

    @Autowired
    private TaskPriorityService taskPriorityService;
    @MockitoBean
    private AIAgentService aiAgentService;

    @Test
    @DisplayName("Should find all TaskPriority mapped by code")
    void shouldFindAllTaskPriorityMappedByCode() {
        Map<TaskPriorityCodeEnum, TaskPriority> map = taskPriorityService.findAllMappedByCode();
        Assertions.assertNotNull(map);
        Assertions.assertEquals(map.size(), TaskPriorityCodeEnum.values().length);
    }
}