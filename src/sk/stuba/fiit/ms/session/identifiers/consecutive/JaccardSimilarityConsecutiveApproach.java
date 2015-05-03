package sk.stuba.fiit.ms.session.identifiers.consecutive;

import sk.stuba.fiit.ms.measures.lexical.JaccardLexicalSimilarity;

/**
 * Lexical baseline using Jaccard similarity.
 */
public final class JaccardSimilarityConsecutiveApproach extends LexicalSimilarityConsecutiveApproach {

    public JaccardSimilarityConsecutiveApproach() {
        super(JaccardLexicalSimilarity.newInstance());
    }

}
