package it.unibo.rag;

import dev.langchain4j.model.embedding.EmbeddingModel;
import it.unibo.utils.Pair;
import it.unibo.utils.Vector;

import java.util.stream.Collectors;

public class SingleInMemoryKnowledge implements Knowledge {
    private final EmbeddingModel model;
    private final java.util.Map<Vector, String> knowledge;
    private final ChunkStrategy chunkStrategy;
    public SingleInMemoryKnowledge(EmbeddingModel model, final ChunkStrategy chunkStrategy, String knowledge) {
        this.model = model;
        this.chunkStrategy = chunkStrategy;
        var chunks = chunkStrategy.chunk(knowledge);
        this.knowledge = chunks.stream()
            .map(data -> Pair.of(data, model.embed(data)))
            .map(pair -> pair.mapY(embedding -> embedding.content().vector()))
            .map(pair -> pair.mapY(Vector::fromFloatArray))
            .collect(Collectors.toMap(Pair::getY, Pair::getX));
    }

    @Override
    public String extract(String text, KnowledgeSelectionStrategy strategy) {
        var request = Vector.fromFloatArray(model.embed(text).content().vector());
        return strategy.select(request, knowledge);
    }

}
