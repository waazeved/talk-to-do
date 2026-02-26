package com.waltsoft.talk_to_do.dot_env;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

@Component
public class DotEnv {

    private final Dotenv dotenv = Dotenv
            .configure()
            .ignoreIfMissing()
            .load();

    public String getUsername() {
        return dotenv.get("USERNAME", "");
    }

    public String getGeminiApiKey() {
        return dotenv.get("GEMINI_API_KEY", "");
    }

    public String getDatabaseUrl() {
        return dotenv.get("DATABASE_URL");
    }

    public String getDatabaseUser() {
        return dotenv.get("DATABASE_USER");
    }

    public String getDatabasePassword() {
        return dotenv.get("DATABASE_PASSWORD");
    }
}
