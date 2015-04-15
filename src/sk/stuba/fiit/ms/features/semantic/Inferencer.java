package sk.stuba.fiit.ms.features.semantic;

import sk.stuba.fiit.ms.semantic.lda.LDAModel;
import sk.stuba.fiit.ms.session.Search;

abstract class Inferencer {

    private final LDAModel model;

    protected Inferencer(final LDAModel model) {
        this.model = model;
    }

    protected double[] getInference(final Search search) {
        return model.inference(search);
    }

}
