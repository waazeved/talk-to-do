package com.waltsoft.talk_to_do.command;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import picocli.CommandLine;

@CommandLine.Command(name = StartChatCommand.COMMAND,
        description = "Start chat with A.I.",
        mixinStandardHelpOptions = true)
public class StartChatCommand implements Runnable {

    static final String COMMAND = "start-chat";
    private static final Log LOGGER = LogFactory.getLog(StartChatCommand.class.getName());

    @Override
    public void run() {
        try {
            System.exit(0);
        } catch (Exception exception) {
            LOGGER.error(exception);
            exception.printStackTrace();
            System.exit(1);
        }
    }

}
