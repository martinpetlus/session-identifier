package sk.stuba.fiit.ms.features.lexical;

import sk.stuba.fiit.ms.features.Util;

public class Jaccard implements Similarity {

    private static Jaccard instance;

    @Override
    public double calculate(final String[] one, final String[] two) {
        return (double) Util.intersection(one, two).length / Util.union(one, two).length;
    }

    public static Jaccard getInstance() {
        if (instance == null) {
            instance = new Jaccard();
        }

        return instance;
    }

}
