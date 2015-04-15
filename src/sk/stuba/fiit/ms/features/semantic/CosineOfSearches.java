package sk.stuba.fiit.ms.features.semantic;

import sk.stuba.fiit.ms.features.PairFeature;
import sk.stuba.fiit.ms.features.Statistic;
import sk.stuba.fiit.ms.semantic.lda.LDAModel;
import sk.stuba.fiit.ms.session.Search;

public final class CosineOfSearches implements PairFeature {

    private final LDAModel model;

    public CosineOfSearches(final LDAModel model) {
        this.model = model;
    }

    @Override
    public double extract(final Search search, final Search compareTo) {
        double[] inferences1 = model.inference(search);
        double[] inferences2 = model.inference(compareTo);

        return Statistic.cosine(inferences1, inferences2);
    }

}
