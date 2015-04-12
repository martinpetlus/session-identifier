package sk.stuba.fiit.ms.features;

import sk.stuba.fiit.ms.session.Search;

public interface PairFeature {

    public double extract(final Search search, final Search compareTo);

}
