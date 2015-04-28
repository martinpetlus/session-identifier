package sk.stuba.fiit.ms.features.temporal;

import sk.stuba.fiit.ms.features.SessionFeature;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

public final class TemporalDistance implements SessionFeature {

    @Override
    public double extract(final Session session, final Search search) {
        Search newestSearch = session.getNewestSearch();

        if (newestSearch != null) {
            return Math.abs(search.getTimeStamp() - newestSearch.getTimeStamp());
        } else {
            return 0.0;
        }
    }

}
