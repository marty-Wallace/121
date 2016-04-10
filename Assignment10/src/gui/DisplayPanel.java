package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * Class to hold and display an image on the MosaicGUI
 * @author Martin
 *
 */
public class DisplayPanel extends JComponent {

	private int width,height; // width and height of image 
	
	private BufferedImage displayedImage; // image being displayed
	
	public DisplayPanel() {
		displayedImage = null; 
	}
	
	/**
	 * Sets a new image to the screen 
	 * @param image - IMage to be displayed 
	 */
	public void set(BufferedImage image) {
		
		if(image != null) {
				width = image.getWidth();
				height = image.getHeight();

			displayedImage = image;
			repaint();
		}
	}
	

	/**
	 * Clear the current image from the screen 
	 */
	public void clearImage() {
		
		Graphics g = displayedImage.getGraphics();
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, width, height);
		repaint();
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		Dimension size = getSize();
		g.clearRect(0, 0, size.width, size.height);
		if(displayedImage != null) {
			g.drawImage(displayedImage, 0, 0, null);
		}
	}
}
