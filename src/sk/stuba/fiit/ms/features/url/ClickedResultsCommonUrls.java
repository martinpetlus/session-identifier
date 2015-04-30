package sk.stuba.fiit.ms.features.url;

import java.util.List;

import sk.stuba.fiit.ms.session.Search;

public final class ClickedResultsCommonUrls extends CommonUrls {

    @Override
    protected List<String> getUrls(final Search search) {
        return search.getClickedUrls();
    }

}
