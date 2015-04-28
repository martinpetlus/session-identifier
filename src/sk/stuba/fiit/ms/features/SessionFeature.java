package sk.stuba.fiit.ms.features;

import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

public interface SessionFeature {

    double extract(final Session session, final Search search);

}
