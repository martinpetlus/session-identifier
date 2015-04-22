package sk.stuba.fiit.ms.features.time;

import sk.stuba.fiit.ms.features.SessionFeature;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

public final class TimeDifference implements SessionFeature {

    @Override
    public double extract(final Session session, final Search search) {
        Search lastSearch = session.getLastSearch();

        if (lastSearch != null) {
            return search.getTimeStamp() - lastSearch.getTimeStamp();
        } else {
            return 0.0;
        }
    }

}
