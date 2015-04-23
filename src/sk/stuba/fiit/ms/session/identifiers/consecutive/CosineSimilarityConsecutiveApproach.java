package sk.stuba.fiit.ms.session.identifiers.consecutive;

import sk.stuba.fiit.ms.similarities.lexical.CosineLexicalSimilarity;

public final class CosineSimilarityConsecutiveApproach extends LexicalSimilarityConsecutiveApproach {

    public CosineSimilarityConsecutiveApproach() {
        super(CosineLexicalSimilarity.newInstance());
    }

}
