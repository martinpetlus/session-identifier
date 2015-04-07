package sk.stuba.fiit.ms.features.time;

import sk.stuba.fiit.ms.features.PairFeature;
import sk.stuba.fiit.ms.session.SearchResult;

public class SpentTimeOnClicks implements PairFeature {

    @Override
    public double extract(final SearchResult searchResult, final SearchResult compareTo) {
        return searchResult.getSpentTimeOnClicks() - compareTo.getSpentTimeOnClicks();
    }

}
