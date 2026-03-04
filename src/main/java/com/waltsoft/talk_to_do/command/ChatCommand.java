package com.waltsoft.talk_to_do.command;


import com.waltsoft.talk_to_do.business.ai_agent.AIAgentService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import picocli.CommandLine;

import java.io.IOException;

@CommandLine.Command(name = ChatCommand.COMMAND,
        description = "Start chat with A.I. agent.",
        mixinStandardHelpOptions = true)
public class ChatCommand implements Runnable {

    static final String COMMAND = "start";
    private static final String EXIT_COMMAND = "exit";
    private static final Log LOGGER = LogFactory.getLog(ChatCommand.class.getName());

    private final AIAgentService aiAgentService;

    public ChatCommand(AIAgentService aiAgentService) {
        this.aiAgentService = aiAgentService;
    }

    @Override
    public void run() {
        try (Terminal terminal = TerminalBuilder
                .builder()
                .system(true) //NOSONAR
                .streams(System.in, System.out) //NOSONAR
                .nativeSignals(true)
                .signalHandler(Terminal.SignalHandler.SIG_IGN)
                .build()) {

            terminal
                    .writer()
                    .println("========================================");
            terminal
                    .writer()
                    .println("               TALK TO DO               ");
            terminal
                    .writer()
                    .println("========================================");

            LineReader reader = LineReaderBuilder
                    .builder()
                    .terminal(terminal)
                    .option(LineReader.Option.DISABLE_EVENT_EXPANSION, true)
                    .option(LineReader.Option.BRACKETED_PASTE, true)
                    .build();

            while (true) {
                String userInput = reader.readLine("YOU: ");

                if (EXIT_COMMAND.equalsIgnoreCase(userInput)) {
                    break;
                }

                String response = this.aiAgentService.chat(userInput);

                printAIResponse(terminal, response);

                terminal.flush();
            }

            terminal
                    .writer()
                    .println("Goodbye!");

        } catch (IOException e) {
            LOGGER.error("Error creating terminal: " + e.getMessage());
        }
    }

    private void printAIResponse(Terminal terminal, String response) {
        int maxWidth = terminal.getWidth() > 0 ? terminal.getWidth() - 5:80;

        StringBuilder sb = new StringBuilder();
        String[] words = response.split(" ");
        int currentLineLength = 0;

        for (String word : words) {
            if (currentLineLength + word.length() + 1 > maxWidth) {
                sb.append("\n    ");
                currentLineLength = 4;
            }
            sb
                    .append(word)
                    .append(" ");
            currentLineLength += word.length() + 1;
        }

        terminal
                .writer()
                .println("AI: " + sb
                        .toString()
                        .trim());
        terminal.flush();
    }
}