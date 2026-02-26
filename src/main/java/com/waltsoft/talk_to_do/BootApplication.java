package com.waltsoft.talk_to_do;


import com.waltsoft.talk_to_do.command.ChatCommand;
import com.waltsoft.talk_to_do.dot_env.DotEnv;
import com.waltsoft.talk_to_do.system_property.SystemPropertySetter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import picocli.CommandLine;

@SpringBootApplication
@ComponentScan(basePackages = "com.waltsoft.talk_to_do")
@EntityScan(basePackages = "com.waltsoft.talk_to_do")
public class BootApplication implements CommandLineRunner {

    private static final Log LOGGER = LogFactory.getLog(BootApplication.class);

    private final ChatClient.Builder chatClientBuilder;
    private final ChatMemory chatMemory;
    private final DotEnv dotEnv;

    @Autowired
    public BootApplication(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, DotEnv dotEnv) {
        this.chatClientBuilder = chatClientBuilder;
        this.chatMemory = chatMemory;
        this.dotEnv = dotEnv;
    }


    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(BootApplication.class);
        new SystemPropertySetter().set();
        application.run(args);
    }

    @Override
    public void run(String... args) {
        try {
            CommandLine commandLine = new CommandLine(new ChatCommand(chatClientBuilder, chatMemory, dotEnv));
            commandLine.execute(args);
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }
}


