package it.unibo.prompt;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;

import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SelfConsistencyAgent extends BasePromptBasedAgent {
    private final int consistencyLevel;
    
    public SelfConsistencyAgent(ChatLanguageModel model, String promptBase, int consistencyLevel) {
        super(model, promptBase);
        this.consistencyLevel = consistencyLevel;
    }

    @Override
    public String ask(String userMessage) {
        var replies = IntStream.range(0, consistencyLevel).mapToObj(i -> getModel().chat(UserMessage.from(getPromptBase() + userMessage))).toList();
        var groupedReplies = replies.stream().collect(Collectors.groupingBy(data -> data.aiMessage().text()));
        // Find the most common reply
        System.out.println("replies: " + replies.stream().map(data -> data.aiMessage().text()).toList());
        return groupedReplies.entrySet().stream().max(Comparator.comparingInt(entry -> entry.getValue().size())).get().getKey();
    }
}
