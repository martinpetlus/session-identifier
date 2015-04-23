package sk.stuba.fiit.ms.similarities.lexical;

import sk.stuba.fiit.ms.utils.TextNormalizer;

public abstract class LexicalSimilarity {

    public abstract double calculate(final String[] one, final String[] two);

    public final double calculate(final String one, final String two) {
        return calculate(TextNormalizer.split(one), TextNormalizer.split(two));
    }

}
