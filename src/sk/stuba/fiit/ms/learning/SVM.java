package sk.stuba.fiit.ms.learning;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;

public final class SVM {
	
	private final svm_model model;
	
	private SVM(final svm_model model) {
		this.model = model;
	}

	public static SVM train(final double[][] trainSet, final double[] trainLabels) {
		final svm_problem prob = new svm_problem();
		
		int dataLength = trainSet.length;
		
		prob.y = trainLabels;
		prob.l = dataLength;
		prob.x = new svm_node[dataLength][];
		
		for (int i = 0; i < dataLength; i++) {
			double[] features = trainSet[i];
			
			prob.x[i] = new svm_node[features.length];
			
			for (int j = 0; j < features.length; j++) {
				svm_node node = new svm_node();

				node.index = j + 1;
				node.value = features[j];

				prob.x[i][j] = node;
			}
		}

		svm_parameter param = new svm_parameter();

		param.probability = 1;
		param.gamma = 0.5;
		param.nu = 0.5;
		param.C = 1;
		param.svm_type = svm_parameter.C_SVC;
		param.kernel_type = svm_parameter.LINEAR;
		param.cache_size = 20000;
		param.eps = 0.001;

		return new SVM(svm.svm_train(prob, param));
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
		
		/*for (int i = 0; i < totalClasses; i++) {
		    System.out.print("(" + labels[i] + ":" + prob_estimates[i] + ")");
		}
		
		System.out.println(" Prediction:" + v + ")");*/
		
		return Double.compare(v, 1.0) == 0 ? true : false;
	}

	public void printModel() {
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

	/*public Model getModel() {
		final double[][] coef = model.sv_coef;

		final svm_node[][] tSVs = transposeSVs(model.SV);

		final double[] result = new double[tSVs.length];

		for (int row = 0; row < tSVs.length; row++) {
			double res = 0.0;

			for (int col = 0; col < tSVs[row].length; col++) {
				res += tSVs[row][col].value * coef[coef.length - col - 1][row];
			}

			result[row] = res;
		}

		return new Model(result, -model.rho[0]);
	}

	private svm_node[][] transposeSVs(final svm_node[][] SVs) {
		final int tCols = SVs.length;

		final int tRows = SVs[0].length;

		final svm_node[][] tSVs = new svm_node[tRows][];

		for (int i = 0; i < tRows; i++) {
			tSVs[i] = new svm_node[tCols];
		}

		for (int row = 0; row < SVs.length; row++) {
			for (int col = 0; col < SVs[row].length; col++) {
				tSVs[col][row] = SVs[row][col];
			}
		}

		return tSVs;
	}*/

	public static final class Model {

		private final double[] w;

		private final double b;

		private Model(final double[] w, final double b) {
			this.w = w;
			this.b = b;
		}

		public double getB() {
			return b;
		}

		public double[] getW() {
			return w;
		}

		@Override
		public String toString() {
			return "Model[b=" + b + " w=" + java.util.Arrays.toString(w) + "]";
		}

	}
	
}
