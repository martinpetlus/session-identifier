package sk.stuba.fiit.ms.features.number;

import sk.stuba.fiit.ms.features.PairFeature;
import sk.stuba.fiit.ms.session.SearchResult;

public final class NumberOfClicks implements PairFeature {

    @Override
    public double extract(final SearchResult searchResult, final SearchResult compareTo) {
        return searchResult.getNumberOfClicks() - searchResult.getNumberOfClicks();
    }

}
