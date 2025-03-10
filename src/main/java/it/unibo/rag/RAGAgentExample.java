package it.unibo.rag;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.DimensionAwareEmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import it.unibo.prompt.PromptBasedAgent;
import it.unibo.prompt.zero.ZeroShotAgent;
import it.unibo.utils.Resources;
import it.unibo.utils.Vector;

public class RAGAgentExample {
    public static void main(String[] args) throws Exception {
        DimensionAwareEmbeddingModel embeddingModel = OllamaEmbeddingModel.builder()
            .baseUrl("http://localhost:11434")
            .modelName("mxbai-embed-large")
            .logRequests(true)
            .logResponses(true)
            .build();
        final var model = OllamaChatModel.builder()
            .baseUrl("http://localhost:11434")
            .logRequests(true)
            .logResponses(true)
            .modelName("smollm")
            .numPredict(128)
            .build();
        final var toAsk = "What is hypertension?";
        final var knowledgeData = Resources.loadAllContent("hypertension.txt");
        final var knowledge = new SingleInMemoryKnowledge(embeddingModel, ChunkStrategy.paragraphs(), knowledgeData);
        var groundTruth = knowledge.extract(toAsk, KnowledgeSelectionStrategy.closest());
        var ragAgent = new RAGAgent(knowledge, model, KnowledgeSelectionStrategy.closest());
        var zeroShotWithAll = new ZeroShotAgent(model, "this is your knowledge" + knowledgeData + "reply with the right answer: ");
        var zeroShotWithJustQuestion = new ZeroShotAgent(model, "reply with the right answer: ");
        var replyRag = ragAgent.ask(toAsk);
        var zeroShowWithAll = zeroShotWithAll.ask(toAsk);
        var zeroShowWithJustQuestion = zeroShotWithJustQuestion.ask(toAsk);
        System.out.println("RAG Reply: " + replyRag);
        System.out.println("ZeroShot with all Reply: " + zeroShowWithAll);
        System.out.println("ZeroShot with just question Reply: " + zeroShowWithJustQuestion);
        Vector questionVector = Vector.fromFloatArray(embeddingModel.embed(groundTruth).content().vector());
        Vector ragVector = Vector.fromFloatArray(embeddingModel.embed(replyRag).content().vector());
        Vector zeroShotWithAllVector = Vector.fromFloatArray(embeddingModel.embed(zeroShowWithAll).content().vector());
        Vector zeroShotWithJustQuestionVector = Vector.fromFloatArray(embeddingModel.embed(zeroShowWithJustQuestion).content().vector());
        // Compare the vectors with the question vector
        System.out.println("RAG similarity: " + questionVector.cosineSimilarity(ragVector));
        System.out.println("ZeroShot with all similarity: " + questionVector.cosineSimilarity(zeroShotWithAllVector));
        System.out.println("ZeroShot with just question similarity: " + questionVector.cosineSimilarity(zeroShotWithJustQuestionVector));

    }
}
