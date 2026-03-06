package com.waltsoft.talk_to_do.dot_env;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

@Component
public class DotEnv {

    private final Dotenv env = Dotenv
            .configure()
            .ignoreIfMissing()
            .load();

    public String getUsername() {
        return env.get("USERNAME", "");
    }

    public String getGeminiApiKey() {
        return env.get("GEMINI_API_KEY", "");
    }

    public String getDatabaseUrl() {
        return env.get("DATABASE_URL");
    }

    public String getDatabaseUser() {
        return env.get("DATABASE_USER");
    }

    public String getDatabasePassword() {
        return env.get("DATABASE_PASSWORD");
    }
}
