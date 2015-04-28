package sk.stuba.fiit.ms.measures.lexical;

import sk.stuba.fiit.ms.utils.TextNormalizer;

public abstract class LexicalSimilarity implements LexicalMeasure {

    public abstract double calculate(final String[] one, final String[] two);

    @Override
    public final double calculate(final String one, final String two) {
        return calculate(TextNormalizer.split(one), TextNormalizer.split(two));
    }

}
