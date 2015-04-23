package sk.stuba.fiit.ms.similarities.lexical;

import sk.stuba.fiit.ms.features.Util;

public final class JaccardLexicalSimilarity implements LexicalSimilarity {

    @Override
    public double calculate(final String[] one, final String[] two) {
        return (double) Util.intersection(one, two).length / Util.union(one, two).length;
    }

    public static JaccardLexicalSimilarity newInstance() {
        return new JaccardLexicalSimilarity();
    }

}
