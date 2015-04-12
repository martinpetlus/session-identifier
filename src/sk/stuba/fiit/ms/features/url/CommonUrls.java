package sk.stuba.fiit.ms.features.url;

import java.util.List;

import sk.stuba.fiit.ms.features.PairFeature;
import sk.stuba.fiit.ms.features.SessionFeature;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

public final class CommonUrls implements PairFeature, SessionFeature {

    @Override
    public double extract(final Search search, final Search compareTo) {
        List<String> urls = compareTo.getResultsUrls();

        int count = 0;

        for (String url : search.getResultsUrls()) {
            if (urls.contains(url)) {
                count++;
            }
        }

        return count;
    }

    @Override
    public double extract(final Session session, final Search search) {
        int count = 0;

        for (Search sr : session.getAllSearchResults()) {
            count += (int) this.extract(sr, search);
        }

        return count;
    }

}
