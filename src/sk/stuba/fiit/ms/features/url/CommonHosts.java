package sk.stuba.fiit.ms.features.url;

import sk.stuba.fiit.ms.session.Search;

import java.util.List;

public final class CommonHosts extends Common {

    @Override
    protected List<String> map(final Search search) {
        return Util.mapToHosts(search.getResultsUrls());
    }
}
