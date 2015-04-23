package sk.stuba.fiit.ms.session.identifiers.consecutive;

import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.similarities.lexical.CosineLexicalSimilarity;
import sk.stuba.fiit.ms.similarities.lexical.LexicalSimilarity;

public final class CosineSimilarityConsecutiveApproach implements ConsecutiveApproach {

    private final LexicalSimilarity lexicalSimilarity;

    public CosineSimilarityConsecutiveApproach() {
        lexicalSimilarity = CosineLexicalSimilarity.newInstance();
    }

    @Override
    public boolean isSameSession(final Search previous, final Search current) {
        return lexicalSimilarity.calculate(previous.getQuery(), current.getQuery()) > 0.5;
    }

}
