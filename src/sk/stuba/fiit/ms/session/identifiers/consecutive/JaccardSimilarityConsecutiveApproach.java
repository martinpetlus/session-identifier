package sk.stuba.fiit.ms.session.identifiers.consecutive;

import sk.stuba.fiit.ms.measures.lexical.JaccardLexicalSimilarity;

public final class JaccardSimilarityConsecutiveApproach extends LexicalSimilarityConsecutiveApproach {

    public JaccardSimilarityConsecutiveApproach() {
        super(JaccardLexicalSimilarity.newInstance());
    }

}
