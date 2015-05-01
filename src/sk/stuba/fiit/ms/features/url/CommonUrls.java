package sk.stuba.fiit.ms.features.url;

import java.util.List;

import sk.stuba.fiit.ms.session.Search;

public final class CommonUrls extends Common {

    @Override
    protected List<String> map(final Search search) {
        return search.getResultsUrls();
    }

}
