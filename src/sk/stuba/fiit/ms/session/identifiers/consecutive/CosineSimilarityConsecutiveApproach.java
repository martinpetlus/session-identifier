package sk.stuba.fiit.ms.session.identifiers.consecutive;

import sk.stuba.fiit.ms.session.Search;

public final class CosineSimilarityConsecutiveApproach implements ConsecutiveApproach {

    @Override
    public boolean isSameSession(final Search previous, final Search current) {
        return false;
    }

}
