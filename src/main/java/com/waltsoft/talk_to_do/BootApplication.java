package com.waltsoft.talk_to_do;


import com.waltsoft.talk_to_do.command.StartChatCommand;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import picocli.CommandLine;

@SpringBootApplication
@ComponentScan(basePackages = "com.waltsoft.talk_to_do")
@EntityScan(basePackages = "com.waltsoft.talk_to_do")
@CommandLine.Command(name = "run", mixinStandardHelpOptions = true, version = "1.0", description = "Run commands")
public class BootApplication implements CommandLineRunner {

	private static final Log LOGGER = LogFactory.getLog(BootApplication.class);

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(BootApplication.class);
		application.run(args);
	}

	@Override
	public void run(String... args) {
		try {
			CommandLine commandLine = new CommandLine(new BootApplication());
			commandLine.addSubcommand(new StartChatCommand());
			commandLine.execute(args);
		} catch (Exception exception) {
			LOGGER.error(exception);
			System.exit(1);
		}
	}

}


