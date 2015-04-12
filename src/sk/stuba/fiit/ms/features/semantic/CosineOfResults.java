package sk.stuba.fiit.ms.features.semantic;

import sk.stuba.fiit.ms.features.PairFeature;
import sk.stuba.fiit.ms.features.Statistic;
import sk.stuba.fiit.ms.semantic.lda.LDAModel;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Result;

import java.util.List;

public final class CosineOfResults extends Inferencer implements PairFeature {

    public CosineOfResults(final LDAModel model) {
        super(model);
    }

    @Override
    public double extract(final Search search, final Search compareTo) {
        List<Result> results1 = search.getResults();
        List<Result> results2 = compareTo.getResults();

        double inferences = 0.0;

        double[][] inferences1 = super.getInferences(results1);
        double[][] inferences2 = super.getInferences(results2);

        for (int i = 0; i < inferences1.length; i++) {
            for (int j = 0; j < inferences2.length; j++) {
                inferences += Statistic.cosine(inferences1[i], inferences2[j]);
            }
        }

        return inferences / (inferences1.length * inferences2.length);
    }

}
