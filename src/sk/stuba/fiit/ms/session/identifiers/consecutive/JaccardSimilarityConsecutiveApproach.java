package sk.stuba.fiit.ms.session.identifiers.consecutive;

import sk.stuba.fiit.ms.similarities.lexical.JaccardLexicalSimilarity;

public final class JaccardSimilarityConsecutiveApproach extends LexicalSimilarityConsecutiveApproach {

    public JaccardSimilarityConsecutiveApproach() {
        super(JaccardLexicalSimilarity.newInstance());
    }

}
