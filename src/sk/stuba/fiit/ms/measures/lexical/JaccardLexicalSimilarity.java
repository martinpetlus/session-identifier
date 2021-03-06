package sk.stuba.fiit.ms.measures.lexical;

import sk.stuba.fiit.ms.utils.SetUtils;

/**
 * Class that implements Jaccard similarity.
 */
public final class JaccardLexicalSimilarity extends LexicalSimilarity {

    private JaccardLexicalSimilarity() {}

    @Override
    public double calculate(final String[] one, final String[] two) {
        return (double) SetUtils.intersection(one, two).length / SetUtils.union(one, two).length;
    }

    public static JaccardLexicalSimilarity newInstance() {
        return new JaccardLexicalSimilarity();
    }

}
