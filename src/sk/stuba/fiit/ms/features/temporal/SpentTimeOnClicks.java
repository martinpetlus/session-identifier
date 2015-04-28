package sk.stuba.fiit.ms.features.temporal;

import sk.stuba.fiit.ms.features.PairFeature;
import sk.stuba.fiit.ms.session.Search;

public class SpentTimeOnClicks implements PairFeature {

    @Override
    public double extract(final Search search, final Search compareTo) {
        return search.getSpentTimeOnClicks() - compareTo.getSpentTimeOnClicks();
    }

}
