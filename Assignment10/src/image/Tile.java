package image;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Tile implements Comparable<Tile>{
	public int averageRed;
	public int averageGreen;
	public int averageBlue;
	public int score = 0;
	public BufferedImage image;

	//Constructor receives an image, and calculates it's average
	public Tile(BufferedImage i){
		image = i;
		calculateAverage();
		System.out.println();
	}

	//Calculate the average RGB for the local image
	//local variables are updated, nothing is returned.
	private void calculateAverage(){
		int width          = image.getWidth();
		int height         = image.getHeight();
		long red = 0, green = 0, blue = 0;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Color pixel = new Color(image.getRGB(x, y));
				red += pixel.getRed();
				green += pixel.getGreen();
				blue += pixel.getBlue();
			}
		}
		int numPixels = width * height;
		averageRed = (int)(red / numPixels);
		averageGreen = (int)(green / numPixels);
		averageBlue = (int)(blue / numPixels);
	}
	
	@Override
	public String toString(){
		return "Average RGB " + averageRed + ", " + averageGreen + ", " + averageBlue;
	}
	
	@Override
	public int compareTo(Tile t) {
		
		if(t.score > this.score ) {
			return -1;
		}
		
		if(t.score < this.score) {
			return 1;
		}
		return 0;
	}
}