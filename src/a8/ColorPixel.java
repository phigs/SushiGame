package a8;

public class ColorPixel implements Pixel {

	private double red;
	private double green;
	private double blue;
	
	private static final double RED_INTENSITY_FACTOR = 0.299;
	private static final double GREEN_INTENSITY_FACTOR = 0.587;
	private static final double BLUE_INTENSITY_FACTOR = 0.114;

	private static final char[] PIXEL_CHAR_MAP = {'#', 'M', 'X', 'D', '<', '>', 's', ':', '-', ' ', ' '};
	
	public ColorPixel(double r, double g, double b) {
		if (r > 1.0 || r < 0.0) {
			throw new IllegalArgumentException("Red out of bounds");
		}
		if (g > 1.0 || g < 0.0) {
			throw new IllegalArgumentException("Green out of bounds");
		}
		if (b > 1.0 || b < 0.0) {
			throw new IllegalArgumentException("Blue out of bounds");
		}
		red = r;
		green = g;
		blue = b;
	}
	
	@Override
	public double getRed() {
		return red;
	}

	@Override
	public double getBlue() {
		return blue;
	}

	@Override
	public double getGreen() {
		return green;
	}

	@Override
	public double getIntensity() {
		return RED_INTENSITY_FACTOR*getRed() + 
				GREEN_INTENSITY_FACTOR*getGreen() + 
				BLUE_INTENSITY_FACTOR*getBlue();
	}
	
	@Override
	public char getChar() {
		int char_idx = (int) (getIntensity()*10.0);
		return PIXEL_CHAR_MAP[char_idx];
	}
	public Pixel blend(Pixel p, double weight) {
		if(p == null || p.getIntensity() > 1 || p.getIntensity() < 0 || weight > 1 || weight <0){
			throw new RuntimeException();
		}
		double tempr = (this.getRed()*(weight)) + (p.getRed()*(1-weight));
		double tempb = (this.getBlue()*(weight)) + (p.getBlue()*(1-weight));
		double tempg = (this.getGreen()*(weight)) + (p.getGreen()*(1-weight));
		Pixel temp = new ColorPixel(tempr,tempg,tempb);
		return temp;
	}
	public Pixel lighten(double factor) {
		if(factor == 1){
			return new ColorPixel(1.0,1.0,1.0);
		}
		if(factor == 0){
			return this;
		}
		
		if(factor > 1 ||  factor < 0)
			throw new RuntimeException();
		else{
			Pixel temp = new ColorPixel(1.0,1.0,1.0);
			double red = this.blend(temp,1-factor).getRed();
			double blue = this.blend(temp, 1-factor).getBlue();
			double green = this.blend(temp, 1-factor).getGreen();
			return new ColorPixel(red,green,blue);
		}
	}
	public Pixel darken(double factor) {
		if(factor == 1){
			return new ColorPixel(0,0,0);
		}
		if(factor == 0){
			return this;
		}
		if(factor > 1 ||  factor < 0)
			throw new RuntimeException();
		else{
			Pixel temp = new ColorPixel(0,0,0);
			double red = this.blend(temp,1-factor).getRed();
			double blue = this.blend(temp, 1-factor).getBlue();
			double green = this.blend(temp, 1-factor).getGreen();
			return new ColorPixel(red,green,blue);
		}
	}
}
