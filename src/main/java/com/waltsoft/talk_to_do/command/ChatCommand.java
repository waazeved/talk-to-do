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

    public static final String EXIT_COMMAND = "exit";
    static final String COMMAND = "start";
    private static final Log LOGGER = LogFactory.getLog(ChatCommand.class.getName());
    private final AIAgentService aiAgentService;

    public ChatCommand(AIAgentService aiAgentService) {
        this.aiAgentService = aiAgentService;
    }

    @Override
    public void run() {
        try (Terminal terminal = TerminalBuilder
                .builder()
                .system(true)
                .streams(System.in, System.out) // Força o JLine a usar as streams padrão
                .nativeSignals(true)            // Permite capturar sinais do SO
                .signalHandler(Terminal.SignalHandler.SIG_IGN) // Evita que o Gradle mate a thread ao ler
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
            System.err.println("Error creating terminal: " + e.getMessage());
        }

    }

    private void printAIResponse(Terminal terminal, String response) {
        int maxWidth = terminal.getWidth() > 0 ? terminal.getWidth() - 5:80;

        // Divide o texto em palavras e reconstrói com quebras de linha
        StringBuilder sb = new StringBuilder();
        String[] words = response.split(" ");
        int currentLineLength = 0;

        for (String word : words) {
            if (currentLineLength + word.length() + 1 > maxWidth) {
                sb.append("\n    "); // Indentação para a IA parecer um bloco
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