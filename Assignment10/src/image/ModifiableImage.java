package image;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ModifiableImage extends BufferedImage{
	
	public ModifiableImage(BufferedImage image) {
		super(image.getColorModel(), image.copyData(null), image.isAlphaPremultiplied(), null);
		
	}
	
	/**
	 * Create a ModifiableImage of a specific size
	 * @param width 
	 * @param height
	 */
	public ModifiableImage(int width, int height) {
		super(width, height, TYPE_INT_RGB);
	}
	
	public void setPixel(int i, int j, Color color) {
		int pixel = color.getRGB();
		setRGB(i, j, pixel);
	}

	public Color getPixel(int i, int j) {
		int pixel = getRGB(i, j);
		return new Color(pixel);
	}
}
