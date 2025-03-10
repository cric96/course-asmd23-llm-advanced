package it.unibo.requirements;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import it.unibo.prompt.zero.ZeroShotAgent;
import it.unibo.utils.Resources;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class RequirementClassification {
    public static void main(String[] args) throws Exception {
        final ChatLanguageModel model = GoogleAiGeminiChatModel.builder()
            .modelName("gemini-2.0-flash")
            .apiKey(System.getenv("API_KEY"))
            .build();
        final var task = """
            I will send to you several requirements for a software application.
            Classify them following the IEEE standard for software requirements specification.
            Reply JUST with the classification of each requirement.
            If you need more information, just ask.
        """;

        final var zeroShot = new ZeroShotAgent(model, task);
        final var query = Resources.loadAllContent("requirements.txt");
        var requirements = query.split("\n");
        for (var requirement : requirements) {
            var reply = zeroShot.ask(requirement);
            System.out.println("Requirement " + requirement + " -- CLASSIFICATION: " + reply);
        }
    }
}
