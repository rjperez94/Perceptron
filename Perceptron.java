import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Code adapted from:
 *    https://github.com/RichardKnop/ansi-c-perceptron
 *    and
 *    https://github.com/nsadawi/perceptron/blob/master/Perceptron.java
 *
 */
public class Perceptron {
	private static JFileChooser fileChooser = new JFileChooser();
	private static final String TRAINSET_FILENAME = "image.data";

	private static final int NUM_FEATURES = 100;
	private static final int MAX_REPEAT = 1000;
	private static final double THRESHOLD = 0.5;

	private static final double RATE_INCREMENT = 0.95;
	private static final double MINIMUM = 0.01;

	private static Feature[] features = new Feature[NUM_FEATURES];
	private static List<Image> images = new ArrayList<Image>();

	private static int width = 10;
	private static int height = 10;
	private static double learningRate = 1;

	public static void main(String[] args) {
		chooseDir();

		features();
		trainPerceptron();
	}

	private static void chooseDir() {
		File train = null;

		// set up the file chooser
		fileChooser.setCurrentDirectory(new File("."));
		fileChooser.setDialogTitle("Select input directory");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		// run the file chooser and check the user didn't hit cancel
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			// get the files in the selected directory and match them to
			// the files we need.
			File directory = fileChooser.getSelectedFile();
			File[] files = directory.listFiles();

			for (File f : files) {
				if (f.getName().equals(TRAINSET_FILENAME)) {
					train = f;
				}
			}

			// check none of the files are missing, and call the load
			// method in your code.
			if (train == null) {
				JOptionPane.showMessageDialog(null, "Directory does not contain correct files", "Error",
						JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			} else {
				readFile(train);
			}
		}
	}

	private static void readFile(File train) {
		try {
			Scanner scan = new Scanner(train);
			while (scan.hasNext()) {
				images.add(load(scan));
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//from given code
	public static Image load(Scanner f) {
		boolean[][] newImage = null;
		boolean isX = false;
		java.util.regex.Pattern bit = java.util.regex.Pattern.compile("[01]");
		if (!f.next().equals("P1"))
			System.out.println("Not a P1 PBM file");
		String category = f.next().substring(1);
		if (!category.equals("other"))
			isX = true;
		int rows = f.nextInt();
		int cols = f.nextInt();

		newImage = new boolean[rows][cols];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				newImage[r][c] = (f.findWithinHorizon(bit, 0).equals("1"));
			}
		}

		return new Image(newImage, isX);
	}

	private static void features() {
		features[0] = new Dummy(width, height, 0, 0 - THRESHOLD);

		for (int i = 1; i < NUM_FEATURES; i++) {
			features[i] = new Feature(width, height, i);
		}
	}

	private static void trainPerceptron() {
		int incorrect = images.size();
		int repeats = 0;

		for (int i = 0; i < MAX_REPEAT && incorrect != 0; i++) {
			int correct = 0;

			for (Image image : images) {
				double netInput = 0;

				for (Feature feat: features) {
					netInput += feat.evaluate(image) * feat.weight;
				}

				// eval yes
				if (netInput <= 0) {
					if (image.isYes) { // actual yes
						correct++;
					} else {
						// re-weigh
						for (Feature feat: features) {
							double newWeight = feat.weight + feat.evaluate(image) * learningRate;
							feat.weight = newWeight;
						}
					}
				} else { // eval other
					if (!image.isYes) { // actual other
						correct++;
					} else {
						// re-weigh
						for (Feature feat: features) {
							double newWeight = feat.weight - feat.evaluate(image) * learningRate;
							feat.weight = newWeight;
						}
					}
				}
			}

			//wrong images for cycle
			incorrect = images.size() - correct;

			// Modify the learning rate
			if (learningRate > MINIMUM) {
				learningRate *= RATE_INCREMENT;
			}

			repeats=i+1;
		}

		if (incorrect == 0) {
			System.out.println("Converged! "+repeats+" repeats");
		} else {
			System.out.println("Not converging! "+incorrect+" incorrect");
		}

		System.out.println("Learning Rate " + learningRate*100 + "%\n");

		for (Feature feat: features) {
			System.out.println(feat.toString());
		}

	}
}
