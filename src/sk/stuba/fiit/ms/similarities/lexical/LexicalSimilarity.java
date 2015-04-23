package sk.stuba.fiit.ms.similarities.lexical;

public interface LexicalSimilarity {

    double calculate(final String[] one, final String[] two);

}
