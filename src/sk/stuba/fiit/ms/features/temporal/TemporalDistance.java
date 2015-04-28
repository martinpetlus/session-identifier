package sk.stuba.fiit.ms.features.temporal;

import sk.stuba.fiit.ms.features.SessionFeature;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

public final class TemporalDistance implements SessionFeature {

    @Override
    public double extract(final Session session, final Search search) {
        Search lastSearch = session.getNewestSearch();

        if (lastSearch != null) {
            return Math.abs(search.getTimeStamp() - lastSearch.getTimeStamp());
        } else {
            return 0.0;
        }
    }

}
