package sk.stuba.fiit.ms.features.temporal;

import sk.stuba.fiit.ms.features.SessionFeature;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

public final class TemporalDistanceOldest implements SessionFeature {

    @Override
    public double extract(final Session session, final Search search) {
        return search.getTimeStamp() - session.getOldestSearch().getTimeStamp();
    }

}
