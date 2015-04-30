package sk.stuba.fiit.ms.features.lexical;

import sk.stuba.fiit.ms.measures.lexical.LexicalMeasure;
import sk.stuba.fiit.ms.session.Result;

public final class QueryClickedTitlesMeasure extends QueryClickedMeasure {

    public QueryClickedTitlesMeasure(final LexicalMeasure lexicalMeasure) {
        super(lexicalMeasure);
    }

    @Override
    protected String getResultText(final Result clickedResult) {
        return clickedResult.getTitle();
    }

}
