package sk.stuba.fiit.ms.session.identifiers.consecutive;

import sk.stuba.fiit.ms.measures.lexical.CosineLexicalSimilarity;

public final class CosineSimilarityConsecutiveApproach extends LexicalSimilarityConsecutiveApproach {

    public CosineSimilarityConsecutiveApproach() {
        super(CosineLexicalSimilarity.newInstance());
    }

}
