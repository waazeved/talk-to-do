package com.waltsoft.talk_to_do.business.task_status;

import com.waltsoft.talk_to_do.business.ai_agent.AIAgentService;
import com.waltsoft.talk_to_do.entity.task_status.TaskStatus;
import com.waltsoft.talk_to_do.entity.task_status.enums.TaskStatusCodeEnum;
import com.waltsoft.talk_to_do.test_container.PostgreSQLContainerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Map;


class FindAllMappedByCodeTest extends PostgreSQLContainerTest {

    @Autowired
    private TaskStatusService taskStatusService;
    @MockitoBean
    private AIAgentService aiAgentService;

    @Test
    @DisplayName("Should find all TaskStatus mapped by code")
    void shouldFindAllTaskStatusMappedByCode() {
        Map<TaskStatusCodeEnum, TaskStatus> map = taskStatusService.findAllMappedByCode();
        Assertions.assertNotNull(map);
        Assertions.assertEquals(map.size(), TaskStatusCodeEnum.values().length);
    }
}