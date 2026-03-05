package com.waltsoft.talk_to_do.business.task_category;

import com.waltsoft.talk_to_do.business.ai_agent.AIAgentService;
import com.waltsoft.talk_to_do.dto.task_category.TaskCategoryInsertDto;
import com.waltsoft.talk_to_do.entity.task_category.TaskCategory;
import com.waltsoft.talk_to_do.test_container.PostgreSQLContainerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
class Insert extends PostgreSQLContainerTest {

    private final String CATEGORY_CODE = "WORK";

    @MockitoSpyBean
    private TaskCategoryService taskCategoryService;
    @Autowired
    private TaskCategoryRepository taskCategoryRepository;
    @MockitoBean
    private ApplicationEventPublisher eventPublisher;
    @MockitoBean
    private AIAgentService aiAgentService;

    @Test
    @DisplayName("Should insert new task category")
    void shouldInsertTaskCategory() {

        TaskCategoryInsertDto taskCategoryInsertDto = new TaskCategoryInsertDto(CATEGORY_CODE);
        taskCategoryService.insert(taskCategoryInsertDto);
        List<TaskCategory> foundTaskCategorySet = taskCategoryRepository.findAll();

        Assertions.assertNotNull(foundTaskCategorySet);
        Assertions.assertEquals(1, foundTaskCategorySet.size());

        TaskCategory taskCategory = foundTaskCategorySet
                .stream()
                .findFirst()
                .get();

        Assertions.assertEquals(taskCategoryInsertDto.code(), taskCategory.getCode());

        Mockito
                .verify(eventPublisher, Mockito.times(1))
                .publishEvent(taskCategory);

    }

}