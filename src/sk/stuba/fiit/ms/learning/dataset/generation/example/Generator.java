package sk.stuba.fiit.ms.learning.dataset.generation.example;

import java.util.Random;

import sk.stuba.fiit.ms.session.Session;


public abstract class Generator {
	
	protected final Random random;
	
	protected final Session session;
	
	protected final double[] EMPTY_FEATURES = new double[0]; 
	
	public Generator(final Session session) {
		this.session = session;
		this.random = new Random();
	}
	
	protected int[] randomIndices(final int length, final int max) {
		return randomIndices(length, max, -1);
	}
	
	protected int[] randomIndices(final int length, final int max, final int exclude) {
		int[] randomNumbers = new int[length];
		
		int count = 0;
	
		while (count < length) {
			int randomNumber = random.nextInt(max);
			
			if (exclude != -1 && randomNumber == exclude) {
				continue;
			}
			
			int j = 0;
			
			for (; j < count; j++) {
				if (randomNumbers[j] == randomNumber) {
					break;
				}
			}
			
			if (j == count) {
				randomNumbers[count++] = randomNumber;
			}
		}
		
		return randomNumbers;
	}

	public abstract boolean generatable(int queries);
	
	public abstract double[] generate(int queries);
	
}
