package sk.stuba.fiit.ms.features;

import sk.stuba.fiit.ms.session.SearchResult;
import sk.stuba.fiit.ms.session.Session;

public interface SessionFeature {

    public double extract(final Session session, final SearchResult searchResult);

}
