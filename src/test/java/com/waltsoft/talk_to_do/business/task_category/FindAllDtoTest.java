package com.waltsoft.talk_to_do.business.task_category;

import com.waltsoft.talk_to_do.business.ai_agent.AIAgentService;
import com.waltsoft.talk_to_do.dto.task_category.TaskCategoryDto;
import com.waltsoft.talk_to_do.entity.task_category.TaskCategory;
import com.waltsoft.talk_to_do.test_container.PostgreSQLContainerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
class FindAllDto extends PostgreSQLContainerTest {

    private final String CATEGORY_CODE = "WORK";

    @MockitoSpyBean
    private TaskCategoryService taskCategoryService;
    @MockitoSpyBean
    private TaskCategoryRepository taskCategoryRepository;
    @MockitoBean
    private AIAgentService aiAgentService;

    @Test
    @DisplayName("Should find all TaskCategoryDto using cache")
    void shouldFindAllTaskCategoryUsingCacheDto() {

        TaskCategory taskCategory = makeTaskCategory(CATEGORY_CODE);
        taskCategoryRepository.save(taskCategory);
        TaskCategoryDto dto = makeTaskCategoryDto(taskCategory);
        Set<TaskCategoryDto> expectedTaskCategoryDtoSet = Set.of(dto);
        Set<TaskCategoryDto> foundTaskCategoryDtoSet = this.taskCategoryService.findAllDto();

        Assertions.assertNotNull(foundTaskCategoryDtoSet);
        Assertions.assertEquals(expectedTaskCategoryDtoSet, foundTaskCategoryDtoSet);

        Set<TaskCategoryDto> foundTaskCategoryDtoSetFromCache = this.taskCategoryService.findAllDto();

        Assertions.assertNotNull(foundTaskCategoryDtoSetFromCache);
        Assertions.assertEquals(foundTaskCategoryDtoSet, foundTaskCategoryDtoSetFromCache);

        Mockito
                .verify(taskCategoryRepository, Mockito.times(1))
                .findAll();

    }

    private TaskCategory makeTaskCategory(String code) {
        return new TaskCategory().setCode(code);
    }

    private TaskCategoryDto makeTaskCategoryDto(TaskCategory taskCategory) {
        return new TaskCategoryDto(taskCategory.getId(), taskCategory.getCode());
    }


}