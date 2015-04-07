package sk.stuba.fiit.ms.features;

import sk.stuba.fiit.ms.session.SearchResult;

public interface PairFeature {

    public double extract(final SearchResult searchResult, final SearchResult compareTo);

}
