package com.waltsoft.talk_to_do.command;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import picocli.CommandLine;

import java.util.Collection;

@CommandLine.Command(name = StartChatCommand.COMMAND,
        description = "Start chat with A.I.",
        mixinStandardHelpOptions = true)
public class StartChatCommand implements Runnable {

    static final String COMMAND = "start-chat";
    private static final Log LOGGER = LogFactory.getLog(StartChatCommand.class.getName());

    public StartChatCommand(VectorTilesMaker vectorTilesMaker) {
        this.vectorTilesMaker = vectorTilesMaker;
    }

    @Override
    public void run() {
        try {
            Collection<GeometryAndVectorTilesPayloadDto> geometryAndVectorTilesPayloadDtos = JsonUtils
                    .fromJsonAsCollection(payload, GeometryAndVectorTilesPayloadDto[].class);

            vectorTilesMaker.make(geometryAndVectorTilesPayloadDtos);

            SystemExit.success();
        } catch (Exception exception) {
            SystemExit.error(exception, LOGGER::error);
        }
    }

}
