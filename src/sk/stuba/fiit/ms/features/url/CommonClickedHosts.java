package sk.stuba.fiit.ms.features.url;

import sk.stuba.fiit.ms.session.Search;

import java.util.List;

public final class CommonClickedHosts extends Common {

    @Override
    protected List<String> map(Search search) {
        return Util.mapToHosts(search.getClickedUrls());
    }

}
