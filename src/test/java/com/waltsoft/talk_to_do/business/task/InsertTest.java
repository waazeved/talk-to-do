package com.waltsoft.talk_to_do.business.task;

import com.waltsoft.talk_to_do.business.ai_agent.AIAgentService;
import com.waltsoft.talk_to_do.business.task_category.TaskCategoryService;
import com.waltsoft.talk_to_do.business.task_priority.TaskPriorityService;
import com.waltsoft.talk_to_do.business.task_status.TaskStatusService;
import com.waltsoft.talk_to_do.dto.task.TaskInsertDto;
import com.waltsoft.talk_to_do.entity.task.Task;
import com.waltsoft.talk_to_do.entity.task_category.TaskCategory;
import com.waltsoft.talk_to_do.entity.task_priority.enums.TaskPriorityCodeEnum;
import com.waltsoft.talk_to_do.entity.task_status.enums.TaskStatusCodeEnum;
import com.waltsoft.talk_to_do.test_container.PostgreSQLContainerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
class Insert extends PostgreSQLContainerTest {

    private final String CATEGORY_CODE = "WORK";
    private final TaskPriorityCodeEnum PRIORITY_CODE = TaskPriorityCodeEnum.HIGH;
    private final TaskStatusCodeEnum STATUS_CODE = TaskStatusCodeEnum.IN_PROGRESS;
    private final String TITLE = "Make New Project";
    private final String DESCRIPTION = "Make project to detect fire in camera images";
    private final LocalDateTime START_DATE_TIME = LocalDateTime.now();

    private final LocalDateTime END_DATE_TIME = LocalDateTime
            .now()
            .plusHours(1);


    @MockitoSpyBean
    private TaskService taskService;
    @Autowired
    private TaskPriorityService taskPriorityService;
    @Autowired
    private TaskStatusService taskStatusService;
    @Autowired
    private TaskCategoryService taskCategoryService;
    @MockitoBean
    private ApplicationEventPublisher eventPublisher;
    @MockitoBean
    private AIAgentService aiAgentService;

    private TaskInsertDto taskInsertDto;

    @BeforeEach
    void setup() {
        TaskCategory taskCategory = new TaskCategory().setCode(CATEGORY_CODE);
        taskCategory = taskCategoryService.save(taskCategory);

        taskInsertDto = new TaskInsertDto(STATUS_CODE,
                taskCategory.getId(),
                PRIORITY_CODE, TITLE,
                DESCRIPTION,
                START_DATE_TIME.toString(),
                END_DATE_TIME.toString());
    }

    @Test
    @DisplayName("Should insert new task")
    void shouldInsertTask() {
        taskService.insert(taskInsertDto);
        List<Task> foundTaskSet = taskService.findAll();

        Assertions.assertNotNull(foundTaskSet);
        Assertions.assertEquals(1, foundTaskSet.size());

        Task task = foundTaskSet
                .stream()
                .findFirst()
                .get();

        Assertions.assertEquals(TITLE, task.getTitle());
        Assertions.assertEquals(DESCRIPTION, task.getDescription());

        Assertions.assertEquals(CATEGORY_CODE, task
                .getCategory()
                .getCode());

        Assertions.assertEquals(STATUS_CODE, task
                .getStatus()
                .getCode());

        Assertions.assertEquals(PRIORITY_CODE, task
                .getPriority()
                .getCode());

        Assertions.assertEquals(START_DATE_TIME, task.getStartDateTime());
        Assertions.assertEquals(END_DATE_TIME, task.getEndDateTime());

        Mockito
                .verify(eventPublisher, Mockito.times(1))
                .publishEvent(task);

    }
}