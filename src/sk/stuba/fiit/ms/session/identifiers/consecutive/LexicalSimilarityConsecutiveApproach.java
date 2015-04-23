package sk.stuba.fiit.ms.session.identifiers.consecutive;

import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.similarities.lexical.CosineLexicalSimilarity;
import sk.stuba.fiit.ms.similarities.lexical.LexicalSimilarity;

class LexicalSimilarityConsecutiveApproach {

    private final LexicalSimilarity lexicalSimilarity;

    public LexicalSimilarityConsecutiveApproach(final LexicalSimilarity lexicalSimilarity) {
        this.lexicalSimilarity = lexicalSimilarity;
    }

    public final boolean isSameSession(final Search previous, final Search current) {
        return lexicalSimilarity.calculate(previous.getQuery(), current.getQuery()) > 0.5;
    }

}
