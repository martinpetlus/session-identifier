package sk.stuba.fiit.ms.features.lexical;

import sk.stuba.fiit.ms.measures.lexical.LexicalMeasure;
import sk.stuba.fiit.ms.session.Result;

public class QueryClickedSnippetsMeasure extends QueryClickedMeasure {

    public QueryClickedSnippetsMeasure(final LexicalMeasure lexicalMeasure) {
        super(lexicalMeasure);
    }

    @Override
    protected String getResultText(final Result clickedResult) {
        return clickedResult.getSnippet();
    }

}
