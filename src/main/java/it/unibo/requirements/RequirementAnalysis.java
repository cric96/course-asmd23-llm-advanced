package it.unibo.requirements;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import it.unibo.prompt.zero.ZeroShotAgent;
import it.unibo.utils.Resources;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class RequirementAnalysis {
    public static void main(String[] args) throws Exception {
        final ChatLanguageModel model = GoogleAiGeminiChatModel.builder()
            .modelName("gemini-2.0-flash")
            .apiKey(System.getenv("API_KEY"))
            .build();
        final var task = """
            I will send you several requirements for a software application.
            Help me in analyzing them, highlight the conflicts, and suggest improvements.
        """;

        final var zeroShot = new ZeroShotAgent(model, task);
        final var query = Resources.loadAllContent("requirements.txt");
        System.out.println("Query: " + query);
        var reply = zeroShot.ask(query);
        System.out.println("Reply: " + reply);
    }
}
