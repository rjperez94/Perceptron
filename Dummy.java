public class Dummy extends Feature {
	public Dummy(int width, int height, int seedModifier, double weight) {
		super(width, height, seedModifier);
		this.weight = weight;
	}
	
	@Override
	public double evaluate(Image image) {
		return 1;
	}

}
