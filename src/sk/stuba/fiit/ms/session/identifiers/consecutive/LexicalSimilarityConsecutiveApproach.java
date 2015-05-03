package sk.stuba.fiit.ms.session.identifiers.consecutive;

import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.measures.lexical.LexicalSimilarity;

/**
 * Class used as baseline method for comparing our method with lexical based approaches.
 */
class LexicalSimilarityConsecutiveApproach implements ConsecutiveApproach {

    private final LexicalSimilarity lexicalSimilarity;

    public LexicalSimilarityConsecutiveApproach(final LexicalSimilarity lexicalSimilarity) {
        this.lexicalSimilarity = lexicalSimilarity;
    }

    /**
     * Returns true if lexical similarity between two consecutive searches is higher than 50%.
     * @param previous previous search
     * @param current search after previous search
     * @return true if lexical similarity is higher than 50%
     */
    @Override
    public final boolean isSameSession(final Search previous, final Search current) {
        return Double.compare(lexicalSimilarity.calculate(previous.getQuery(), current.getQuery()), 0.5) > 0;
    }

}
