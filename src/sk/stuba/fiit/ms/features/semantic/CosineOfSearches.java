package sk.stuba.fiit.ms.features.semantic;

import sk.stuba.fiit.ms.features.PairFeature;
import sk.stuba.fiit.ms.features.SessionFeature;
import sk.stuba.fiit.ms.features.Statistic;
import sk.stuba.fiit.ms.semantic.lda.LDAModel;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

public final class CosineOfSearches implements PairFeature, SessionFeature {

    private final LDAModel model;

    public CosineOfSearches(final LDAModel model) {
        this.model = model;
    }

    @Override
    public double extract(final Search search1, final Search search2) {
        double[] inferences1 = model.inference(search1);
        double[] inferences2 = model.inference(search2);

        return Statistic.cosine(inferences1, inferences2);
    }

    @Override
    public double extract(final Session session, final Search search) {
        double sumOfInferences = 0.0;

        double[] searchInferences = model.inference(search);

        for (Search s : session.getAllSearches()) {
            double[] inferences = model.inference(s);

            sumOfInferences += Statistic.cosine(searchInferences, inferences);
        }

        return sumOfInferences / session.getNumberOfSearches();
    }

}
