package com.waltsoft.talk_to_do.dot_env;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

@Component
public class DotEnv {

    private final Dotenv dotEnv = Dotenv
            .configure()
            .ignoreIfMissing()
            .load();

    public String getUsername() {
        return dotEnv.get("USERNAME", "");
    }

    public String getGeminiApiKey() {
        return dotEnv.get("GEMINI_API_KEY", "");
    }

    public String getDatabaseUrl() {
        return dotEnv.get("DATABASE_URL");
    }

    public String getDatabaseUser() {
        return dotEnv.get("DATABASE_USER");
    }

    public String getDatabasePassword() {
        return dotEnv.get("DATABASE_PASSWORD");
    }
}
