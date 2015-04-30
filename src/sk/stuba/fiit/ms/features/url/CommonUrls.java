package sk.stuba.fiit.ms.features.url;

import sk.stuba.fiit.ms.features.PairFeature;
import sk.stuba.fiit.ms.features.SessionFeature;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

import java.util.List;

abstract class CommonUrls implements PairFeature, SessionFeature {

    protected abstract List<String> getUrls(final Search search);

    @Override
    public final double extract(final Search search1, final Search search2) {
        List<String> urls = getUrls(search1);

        int count = 0;

        for (String url : getUrls(search2)) {
            if (urls.contains(url)) {
                count++;
            }
        }

        return count;
    }

    @Override
    public final double extract(final Session session, final Search search) {
        int count = 0;

        for (Search s : session.getAllSearches()) {
            count += (int) this.extract(s, search);
        }

        return count;
    }

}
