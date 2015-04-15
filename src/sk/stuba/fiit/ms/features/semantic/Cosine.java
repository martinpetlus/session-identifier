package sk.stuba.fiit.ms.features.semantic;

import sk.stuba.fiit.ms.features.PairFeature;
import sk.stuba.fiit.ms.features.Statistic;
import sk.stuba.fiit.ms.semantic.lda.LDAModel;
import sk.stuba.fiit.ms.session.Search;

public final class Cosine extends Inferencer implements PairFeature {

    public Cosine(final LDAModel model) {
        super(model);
    }

    @Override
    public double extract(final Search search, final Search compareTo) {
        double[] inferences1 = super.getInference(search);
        double[] inferences2 = super.getInference(compareTo);

        return Statistic.cosine(inferences1, inferences2);
    }

}
