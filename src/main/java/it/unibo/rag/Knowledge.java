package it.unibo.rag;

public interface Knowledge {
    String extract(String text, KnowledgeSelectionStrategy strategy);
}
