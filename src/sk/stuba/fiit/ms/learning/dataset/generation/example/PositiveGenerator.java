package sk.stuba.fiit.ms.learning.dataset.generation.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sk.stuba.fiit.ms.features.extract.SessionExtractor;
import sk.stuba.fiit.ms.session.Search;
import sk.stuba.fiit.ms.session.Session;

public final class PositiveGenerator extends Generator {

    public PositiveGenerator(final SessionExtractor extractor, final Session session) {
        super(extractor, session);
    }

    private Session getSessionWithRandomSearches(final int excludeIndex, final int numberOfSearches) {
        List<Search> randomSearches = new ArrayList<Search>();

        Session session = this.getSession();

        int[] randomIndices = Generator.randomIndices(numberOfSearches, session.getNumberOfSearches(), excludeIndex);

        for (int i = 0; i < numberOfSearches; i++) {
            randomSearches.add(session.getSearch(randomIndices[i]));
        }

        return new Session(randomSearches);
    }

    @Override
    public double[][] generate(final int queries) {
        if (!canGenerate(queries)) {
            throw new IllegalArgumentException("Illegal number of queries");
        }

        List<Search> searches = this.getSession().getAllSearches();

//        double[][] features = new double[searches.size()][];
        double[][] features = new double[1][];

        Random random = new Random();

        int randomIndex = random.nextInt(searches.size());

        Search search = searches.get(randomIndex);

        Session session = getSessionWithRandomSearches(randomIndex, queries);

        features[0] = this.getSessionExtractor().extractFeatures(session, search);

//        for (int i = 0; i < searches.size(); i++) {
//            Search search = searches.get(i);
//
//            Session session = getSessionWithRandomSearches(i, queries);
//
//            features[i] = this.getSessionExtractor().extractFeatures(session, search);
//        }

        return features;
    }

    @Override
    public boolean canGenerate(final int queries) {
        return this.getSession().getNumberOfSearches() > queries;
    }

}
