package sk.stuba.fiit.ms.features.semantic;

import sk.stuba.fiit.ms.semantic.lda.LDAModel;
import sk.stuba.fiit.ms.session.Result;

import java.util.List;

abstract class Inferencer {

    private final LDAModel model;

    protected Inferencer(final LDAModel model) {
        this.model = model;
    }

    protected double[] getInference(final Result result) {
        return model.inference(result);
    }

    protected double[][] getInferences(final List<Result> results) {
        double[][] inferences = new double[results.size()][];

        for (int i = 0; i < results.size(); i++) {
            inferences[i] = getInference(results.get(i));
        }

        return inferences;
    }

}
