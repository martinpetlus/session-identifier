package sk.stuba.fiit.ms.features.number;

import sk.stuba.fiit.ms.features.PairFeature;
import sk.stuba.fiit.ms.session.Search;

public final class NumberOfClicks implements PairFeature {

    @Override
    public double extract(final Search search, final Search compareTo) {
        return search.getNumberOfClicks() - search.getNumberOfClicks();
    }

}
