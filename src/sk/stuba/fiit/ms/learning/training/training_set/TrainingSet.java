package sk.stuba.fiit.ms.learning.training.training_set;

import java.util.ArrayList;
import java.util.List;

public final class TrainingSet {

    private final List<double []> examples;

    private final List<Double> labels;

    private int positiveExamples = 0;

    private int negativeExamples = 0;

    public TrainingSet() {
        this.examples = new ArrayList<double[]>();
        this.labels = new ArrayList<Double>();
    }

    public void addPositiveExamples(final double[][] examples) {
        positiveExamples += examples.length;

        addExamples(examples, 1.0);
    }

    public void addNegativeExamples(final double[][] examples) {
        negativeExamples += examples.length;

        addExamples(examples, 0.0);
    }

    private void addExamples(final double[][] examples, final double label) {
        for (int i = 0; i < examples.length; i++) {
            this.examples.add(examples[i]);
            this.labels.add(label);
        }
    }

    public double[][] getExamples() {
        return examples.toArray(new double[examples.size()][]);
    }

    public double[] getLabels() {
        return toPrimitive(labels);
    }

    private static double[] toPrimitive(final List<Double> values) {
        double[] primitives = new double[values.size()];

        int i = 0;

        for (Double value : values) {
            primitives[i++] = value.doubleValue();
        }

        return primitives;
    }

    public int getPositiveExamples() {
        return positiveExamples;
    }

    public int getNegativeExamples() {
        return negativeExamples;
    }

}
