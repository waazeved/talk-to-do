package com.waltsoft.talk_to_do;


import com.waltsoft.talk_to_do.business.ai_agent.AIAgentService;
import com.waltsoft.talk_to_do.command.ChatCommand;
import com.waltsoft.talk_to_do.system_property.SystemPropertySetter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import picocli.CommandLine;

@SpringBootApplication
@ComponentScan(basePackages = "com.waltsoft.talk_to_do")
@EntityScan(basePackages = "com.waltsoft.talk_to_do")
@EnableCaching
public class BootApplication implements CommandLineRunner {

    private static final Log LOGGER = LogFactory.getLog(BootApplication.class);

    private final AIAgentService aiAgentService;

    @Autowired
    public BootApplication(AIAgentService aiAgentService) {
        this.aiAgentService = aiAgentService;
    }


    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(BootApplication.class);
        new SystemPropertySetter().set();
        application.run(args);
    }

    @Override
    public void run(String... args) {
        try {
            CommandLine commandLine = new CommandLine(new ChatCommand(aiAgentService));
            commandLine.execute(args);
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }
}


