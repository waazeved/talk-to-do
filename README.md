# TALK TO DO

**TALK TO DO** is a Spring AI-powered task management agent designed to transform natural language into organized
actions. The system features an intelligent agent named **Norfa**, who acts as an autonomous secretary, orchestrating
database operations through intent recognition and function calling.

The project demonstrates a modern approach to productivity, where the boundary between conversation and data management
is seamless.

## 🧠 The Agent: Norfa

**Norfa** is the intelligence behind TALK TO DO. She isn't just a chatbot; she is a state-aware agent:

* **Autonomous CRUD:** Uses "Function Calling" to create, list, and update tasks without manual input forms.
* **Adaptive Personality:** Her tone changes based on your productivity. She is warm and encouraging when your list is
  clear, but becomes sharp and authoritative if you have overdue commitments.
* **Temporal Awareness:** Interprets relative dates like "tomorrow," "next Friday," or "in two hours" by syncing with
  the system's real-time clock.

---

## 🛠️ Tech Stack

* **Java 21**
* **Spring Boot 3.4.0**
* **Spring AI** (Integration with Google Gemini 1.5 Flash)
* **Spring Data JPA** & **PostgreSQL**
* **Liquibase** (Database versioning)
* **Picocli** & **JLine** (Interactive CLI interface)
* **JUnit 5** & **Testcontainers** (Integration testing with real Docker containers)

---

## 📋 Prerequisites

* **JDK 21** or higher.
* **Docker** (required for running integration tests via Testcontainers).
* A **Google Gemini API Key** (available at [Google AI Studio](https://aistudio.google.com/)).

---

## ⚙️ Configuration

The project uses `java-dotenv` to manage environment variables. Create a `.env` file in the root directory:

```env
DATABASE_URL=jdbc:postgresql://<host>:<port>/<database_name>?user=<user>&password=<password>
GEMINI_API_KEY=your_gemini_api_key_here
```

---

## 🚀 Getting Started

To run the application, you can use the Gradle wrapper:

```bash
./gradlew bootRun
```

> 💡 **Pro Tip for the Best Experience:** Because this project uses **JLine** for its interactive terminal, running the
> application directly from your IDE (like **IntelliJ IDEA**) or executing the **generated JAR file** in a native terminal
> provides a much smoother and richer CLI experience. Running through `./gradlew bootRun` may interfere with JLine's
> advanced features.

Once the CLI starts, you can talk to the agent directly:
> *"Norfa, remind me to buy coffee tomorrow at 8 AM."*

---

## 🧪 Quality Control & Testing

This project enforces strict maintainability and coverage rules using **SonarLint** and **Jacoco**.

* **Static Analysis:** The build will fail if SonarLint detects critical code smells (handled via `sonarlintMain`).
* **Coverage:** Minimum thresholds are set at **80% for Classes** and **60% for Lines**.
* **Git Hooks:** A `pre-commit` hook is configured to run `check` (tests + linting) before every commit to ensure code
  integrity.

To run tests and generate reports:

```bash
./gradlew test
```

---

## 🏗️ Architecture

The project follows a Tool-Based Agent architecture:

1. **Input:** User provides a natural language prompt via the CLI.
2. **Reasoning:** The ChatClient sends the prompt along with the System Context to Gemini.
3. **Tool Discovery:** Gemini identifies which `TaskService` or `TaskCategoryService` methods (tools) are required.
4. **Execution:** The application executes the local Java method and returns the data to the LLM.
5. **Response:** **Norfa** generates a final response based on the execution result and her current persona.

---

## 📄 License

This project is licensed under the **MIT License**.

---
**Developed by WaltSoft** 🚀