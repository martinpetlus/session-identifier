package sk.stuba.fiit.ms.features.semantic;

import java.util.List;

import sk.stuba.fiit.ms.features.PairFeature;
import sk.stuba.fiit.ms.semantic.lda.LDAModel;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Result;

public final class ClickedResults implements PairFeature {

    private final LDAModel model;

    private final double minSharedProb;

    public ClickedResults(final LDAModel model, double minSharedProb) {
        this.model = model;
        this.minSharedProb = minSharedProb;
    }

    @Override
    public double extract(final Search search, final Search compareTo) {
        List<Result> clicked1 = search.getClickedResults();
        List<Result> clicked2 = compareTo.getClickedResults();

        if (!clicked1.isEmpty() && !clicked2.isEmpty()) {
            for (Result res1 : clicked1) {
                for (Result res2 : clicked2) {
                    int topics = model.getNumTopics();

                    double topics1[] = model.inference(res1);
                    double topics2[] = model.inference(res2);

                    for (int i = 0; i < topics; i++) {
                        if (Double.compare(topics1[i], minSharedProb) > 0 &&
                                Double.compare(topics2[i], minSharedProb) > 0) {
                            return 1.0;
                        }
                    }
                }
            }
        }

        return 0.0;
    }

}
