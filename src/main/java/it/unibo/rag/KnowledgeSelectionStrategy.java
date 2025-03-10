package it.unibo.rag;

import it.unibo.utils.Vector;

import java.util.Comparator;
import java.util.Map;

public interface KnowledgeSelectionStrategy {
    String select(Vector question, Map<Vector, String> knowledge);

    static KnowledgeSelectionStrategy closest() {
        return (question, knowledge) -> 
                knowledge.keySet().stream()
                    .max(Comparator.comparingDouble(a -> a.cosineSimilarity(question)))
                    .map(knowledge::get)
                    .orElseThrow();

    }
}
