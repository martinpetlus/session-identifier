package sk.stuba.fiit.ms.learning;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;

// Implementation inspired by http://stackoverflow.com/questions/10792576/libsvm-java-implementation
public final class SVMModel {

    private final svm_model model;

    private SVMModel(final svm_model model) {
        this.model = model;
    }

    public static SVMModel train(final double[][] trainingSet, final double[] trainingLabels) {
        svm_problem prob = new svm_problem();

        int trainingExamples = trainingSet.length;

        prob.y = trainingLabels;
        prob.l = trainingExamples;
        prob.x = new svm_node[trainingExamples][];

        for (int i = 0; i < trainingExamples; i++) {
            double[] features = trainingSet[i];

            prob.x[i] = new svm_node[features.length];

            for (int j = 0; j < features.length; j++) {
                svm_node node = new svm_node();

                node.index = j + 1;
                node.value = features[j];

                prob.x[i][j] = node;
            }
        }

        svm_parameter param = new svm_parameter();

        // Linear kernel uses only C param, so we have to tune only this
        // Default values from:
        // - http://www.csie.ntu.edu.tw/~cjlin/libsvm/
        // - https://github.com/cjlin1/libsvm/blob/master/java/svm_train.java
        param.svm_type = svm_parameter.C_SVC;
        param.kernel_type = svm_parameter.LINEAR;
        param.degree = 3;
        param.gamma = 1.0 / trainingSet[0].length;
        param.coef0 = 0;
        param.nu = 0.5;
        param.cache_size = 100;
        param.C = 3.7;
        param.eps = 0.01;
        param.p = 0.1;
        param.shrinking = 1;
        param.probability = 0;
        param.nr_weight = 0;
        param.weight_label = new int[0];
        param.weight = new double[0];

        return new SVMModel(svm.svm_train(prob, param));
    }

    public boolean predict(final double[] features) {
        svm_node[] nodes = new svm_node[features.length];

        for (int i = 0; i < features.length; i++) {
            svm_node node = new svm_node();

            node.index = i + 1;
            node.value = features[i];

            nodes[i] = node;
        }

        int totalClasses = 2;

        int[] labels = new int[totalClasses];

        svm.svm_get_labels(this.model, labels);

        double[] prob_estimates = new double[totalClasses];

        double v = svm.svm_predict_probability(this.model, nodes, prob_estimates);

        return Double.compare(v, 1.0) == 0;
    }

    public void print() {
        System.out.println("Support vectors:");

        System.out.print("[");

        for (int i = 0; i < model.SV.length; i++) {
            for (int j = 0; j < model.SV[i].length; j++) {
                if (j > 0) {
                    System.out.print(" ");
                }

                System.out.print(model.SV[i][j].value);
            }

            if (i + 1 < model.SV.length) {
                System.out.println(";");
            }
        }

        System.out.println("]");

        System.out.println("Dual coefs:");

        System.out.print("[");

        for (int i = 0; i < model.sv_coef.length; i++) {
            for (int j = 0; j < model.sv_coef[i].length; j++) {
                if (j > 0) {
                    System.out.print(" ");
                }

                System.out.print(model.sv_coef[i][j]);
            }

            if (i + 1 < model.sv_coef.length) {
                System.out.println(";");
            }
        }

        System.out.println("]");
    }

}
