package it.unibo.rag;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import it.unibo.prompt.PromptBasedAgent;

public class RAGAgent implements PromptBasedAgent {
    private final Knowledge knowledge;
    private final ChatLanguageModel model;
    private final KnowledgeSelectionStrategy selectionStrategy;

    public RAGAgent(Knowledge knowledge, ChatLanguageModel model, KnowledgeSelectionStrategy strategy) {
        this.knowledge = knowledge;
        this.model = model;
        this.selectionStrategy = strategy;
    }


    @Override
    public String getPromptBase() {
        return "Giving this knowledge: ";
    }

    @Override
    public String ask(String userMessage) {
        var similar = knowledge.extract(userMessage, this.selectionStrategy);
        var task = getPromptBase() + similar + " \n\n reply to the following question MAINLY based on the knowledge: " + userMessage;
        return model.chat(UserMessage.from(task)).aiMessage().text();
    }
}
