package it.unibo.rag;

import java.util.List;

public interface ChunkStrategy {
    List<String> chunk(String text);
    
    static ChunkStrategy paragraphs() {
        return text -> List.of(text.split("\n\n"));
    }
}
