package com.waltsoft.talk_to_do.business.ai_agent;

import com.waltsoft.talk_to_do.business.task.TaskService;
import com.waltsoft.talk_to_do.business.task_category.TaskCategoryService;
import com.waltsoft.talk_to_do.business.task_priority.TaskPriorityService;
import com.waltsoft.talk_to_do.business.task_status.TaskStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AIAgentService {

    private final TaskService taskService;
    private final TaskStatusService taskStatusService;
    private final TaskCategoryService taskCategoryService;
    private final TaskPriorityService taskPriorityService;

    @Autowired
    public AIAgentService(TaskService taskService, TaskStatusService taskStatusService, TaskCategoryService taskCategoryService, TaskPriorityService taskPriorityService) {
        this.taskService = taskService;
        this.taskStatusService = taskStatusService;
        this.taskCategoryService = taskCategoryService;
        this.taskPriorityService = taskPriorityService;
    }

    @Bean
    @Description("Adiciona uma nova tarefa à lista de afazeres")
    public Function<TaskRequest, String> criarTarefa() {
        return request -> {
            System.out.println("\n[SISTEMA] Executando função: Criar tarefa '" + request.nome() + "'");
            return "Sucesso: Tarefa '" + request.nome() + "' agendada.";
        };
    }


    public record TaskRequest(String nome) {
    }

}
