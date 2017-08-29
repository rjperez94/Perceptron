import java.util.Random;

public class Feature {
	private static final long SEED = 100;
	private static final int NUM_FEATURE_POINTS = 4;

	private int[] row;
	private int[] col;
	private boolean[] sgn;
	public double weight;


	public Feature(int width, int height, int seedModifier) {
		Random random = new Random(SEED + seedModifier);
		this.weight = (random.nextDouble());
		populate(width, height, random);
	}

	private void populate(int width, int height, Random random) {
		row = new int[NUM_FEATURE_POINTS];
		col = new int[NUM_FEATURE_POINTS];
		sgn = new boolean[NUM_FEATURE_POINTS];
		for (int index = 0; index < NUM_FEATURE_POINTS; index++) {
			row[index] = random.nextInt(height);
			col[index] = random.nextInt(width);
			sgn[index] = random.nextBoolean();
		}
	}

	public double evaluate(Image image) {
		int sum = 0;
		for (int i = 0; i < NUM_FEATURE_POINTS; i++) {
			if (image.values[col[i]] [row[i]] == sgn[i]) {
				sum++;
			}
		}
		return (sum >= 3) ? 1 : 0;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("Feature");
		for (int index = 0; index < NUM_FEATURE_POINTS; index++) {
			sb.append("\nPixel " + index + ": " + col[index] + "," + row[index]	+ " = " + sgn[index]);
		}
		sb.append("\nWeight: " + weight);
		sb.append("\n");
		return sb.toString();
	}
}
