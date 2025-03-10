package it.unibo.requirements;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import it.unibo.prompt.zero.ZeroShotAgent;
import it.unibo.utils.Resources;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class UserStoryGeneration {
    public static void main(String[] args) throws Exception {
        final ChatLanguageModel model = GoogleAiGeminiChatModel.builder()
            .modelName("gemini-2.0-flash")
            .apiKey(System.getenv("API_KEY"))
            .build();
        
        final var zeroShot = new ZeroShotAgent(model, "Extract user stories from this description. The user story should be clear, concise, and complete.");
        final var query = Resources.loadAllContent("project-idea.txt");
        final var reply = zeroShot.ask(query);
        System.out.println("Reply: " + reply);
    }
}
