package sk.stuba.fiit.ms.features.number;

import sk.stuba.fiit.ms.features.PairFeature;
import sk.stuba.fiit.ms.session.Search;

public final class NumberOfResultsViews implements PairFeature {

    @Override
    public double extract(final Search search, final Search compareTo) {
        return search.getNumerOfResultsViews() - compareTo.getNumerOfResultsViews();
    }

}
